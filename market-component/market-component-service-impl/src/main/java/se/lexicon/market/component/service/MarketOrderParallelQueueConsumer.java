package se.lexicon.market.component.service;

import com.so4it.queue.ParallelQueueConsumer;
import se.lexicon.market.component.domain.MarketPriceType;
import se.lexicon.market.component.domain.Money;
import se.lexicon.market.component.domain.Side;
import se.lexicon.market.component.entity.MarketOrderEntity;
import se.lexicon.market.componment.dao.MarketOrderDao;

import java.util.Currency;
import java.util.Set;

public class MarketOrderParallelQueueConsumer {


    private MarketOrderDao marketOrderDao;

    //private MarketApiClient marketClient;


    public MarketOrderParallelQueueConsumer(MarketOrderDao marketOrderDao) {
        this.marketOrderDao = marketOrderDao;
    }

    /**
     * This will be called by the parallel queue framework guaranteeing that only one order for the same
     * accoutn id will be handled at the time
     *
     * @param placeMarketOrderEvent
     */
    @ParallelQueueConsumer
    public void placeOrder (MarketOrderEntity placeMarketOrderEvent) {

        //MatchMarketOrder (placeMarketOrderEvent.getMarketOrderEntity());
        MatchMarketOrder (placeMarketOrderEvent);

//        MatchMarketOrder
//            (MarketOrderEntity.builder()
//            .withId(placeMarketOrderEvent.getMarketOrderEntity().getId())
//            .withSsn(placeMarketOrderEvent.getMarketOrderEntity().getSsn())
//            .withAmount(placeMarketOrderEvent.getMarketOrderEntity().getAmount())
//            .withInsertionTimestamp(placeMarketOrderEvent.getMarketOrderEntity().getInsertionTimestamp())
//            .withNoOfItems(placeMarketOrderEvent.getMarketOrderEntity().getNoOfItems())
//            .withSide(placeMarketOrderEvent.getMarketOrderEntity().getSide())
//            .withMarketPriceType(placeMarketOrderEvent.getMarketOrderEntity().getMarketPriceType())
//            .withOrderBookId(placeMarketOrderEvent.getMarketOrderEntity().getOrderBookId())
//            .withMinMaxValue(placeMarketOrderEvent.getMarketOrderEntity().getMinMaxValue())
//            .withInstrument(placeMarketOrderEvent.getMarketOrderEntity().getInstrument())
//            .withNoOfItemsToMatch(placeMarketOrderEvent.getMarketOrderEntity().getNoOfItems())
//            .withAllItemsMatched(false)
//            .build());
    }

    private void MatchMarketOrder (MarketOrderEntity marketOrderEntity) {


        // GET ALL ORDERS, FILTER AGAINST ALL OTHERS BUY/SELL with same Instrument and not fully matched
        Set<MarketOrderEntity> marketOrderEntities = marketOrderDao.readAll
                (MarketOrderEntity.templateBuilder()
                        .withSide(OtherSide(marketOrderEntity.getSide()))
                        .withInstrument(marketOrderEntity.getInstrument())
                        .withAllItemsMatched(false)
                        .build());

        double minMaxValue = 0d;

        boolean allPossibleMatchingFound = false;
        int noOfItemsToMatch = marketOrderEntity.getNoOfItemsToMatch();
        int noOfItemsMatched = 0;

        while (!allPossibleMatchingFound) {

            MarketOrderEntity bestMatchingMarket = null;

            for (MarketOrderEntity matchingMarketOrderEntity : marketOrderEntities) {

                minMaxValue = AmountOf(matchingMarketOrderEntity.getMinMaxValue().getAmount().doubleValue(),
                        matchingMarketOrderEntity.getMinMaxValue().getCurrency(),
                        marketOrderEntity.getMinMaxValue().getCurrency());

                if (marketOrderEntity.getSide().equals(Side.SELL) ?
                        marketOrderEntity.getMinMaxValue().getAmount().doubleValue() <= minMaxValue :
                        marketOrderEntity.getMinMaxValue().getAmount().doubleValue() >= minMaxValue) {

                    bestMatchingMarket = chooseEntity
                            (noOfItemsToMatch, marketOrderEntity.getMinMaxValue().getCurrency(),
                                    bestMatchingMarket,matchingMarketOrderEntity);

                    if (bestMatchingMarket.getNoOfItemsToMatch().equals(noOfItemsToMatch) &&
                            bestMatchingMarket.getMinMaxValue().getCurrency().equals(marketOrderEntity.getMinMaxValue().getCurrency()))
                        break; //Full matching found, exit loop
                }

            } // loop end;

            if (bestMatchingMarket == null){

                //System.out.println("No Match found for: " + marketEntity);
                marketOrderDao.insertOrUpdate(MarketOrderEntity.builder()
                        .withId(marketOrderEntity.getId())
                        .withSsn(marketOrderEntity.getSsn())
                        .withOrderId(marketOrderEntity.getOrderId())
                        .withAmount(marketOrderEntity.getAmount())
                        .withInsertionTimestamp(marketOrderEntity.getInsertionTimestamp())
                        .withNoOfItems(marketOrderEntity.getNoOfItems())
                        .withSide(marketOrderEntity.getSide())
                        .withMarketPriceType(marketOrderEntity.getMarketPriceType())
                        .withOrderBookId(marketOrderEntity.getOrderBookId())
                        .withMinMaxValue(marketOrderEntity.getMinMaxValue())
                        .withInstrument(marketOrderEntity.getInstrument())
                        .withNoOfItemsToMatch(marketOrderEntity.getNoOfItemsToMatch())
                        .withAllItemsMatched(false) // NOW ALLOWED TO BE MATCHED AGAINST NEW INCOMMING ORDERS
                        .build());

                return; //No matching found, exit this procedure
            }

            // Handle the result from the seach

            int itemsRemaining = noOfItemsToMatch - bestMatchingMarket.getNoOfItemsToMatch();
            allPossibleMatchingFound = itemsRemaining <= 0;
            noOfItemsMatched = itemsRemaining > 0 ? bestMatchingMarket.getNoOfItemsToMatch() : noOfItemsToMatch;

            marketOrderEntity = marketOrderDao.insertOrUpdate(MarketOrderEntity.builder()
                    .withId(marketOrderEntity.getId())
                    .withSsn(marketOrderEntity.getSsn())
                    .withOrderId(marketOrderEntity.getOrderId())
                    .withAmount(marketOrderEntity.getAmount())
                    .withInsertionTimestamp(marketOrderEntity.getInsertionTimestamp())
                    .withNoOfItems(marketOrderEntity.getNoOfItems())
                    .withSide(marketOrderEntity.getSide())
                    .withMarketPriceType(marketOrderEntity.getMarketPriceType())
                    .withOrderBookId(marketOrderEntity.getOrderBookId())
                    .withMinMaxValue(marketOrderEntity.getMinMaxValue())
                    .withInstrument(marketOrderEntity.getInstrument())
                    .withNoOfItemsToMatch(marketOrderEntity.getNoOfItemsToMatch() - noOfItemsMatched)
                    .withAllItemsMatched(itemsRemaining == 0)
                    .build());

            //System.out.println("Entity: " + marketEntity);

            MarketOrderEntity marketedEntity = marketOrderDao.update(MarketOrderEntity.builder()
                    .withId(bestMatchingMarket.getId())
                    .withSsn(bestMatchingMarket.getSsn())
                    .withOrderId(bestMatchingMarket.getOrderId())
                    .withAmount(bestMatchingMarket.getAmount())
                    .withInsertionTimestamp(bestMatchingMarket.getInsertionTimestamp())
                    .withNoOfItems(bestMatchingMarket.getNoOfItems())
                    .withSide(bestMatchingMarket.getSide())
                    .withMarketPriceType(bestMatchingMarket.getMarketPriceType())
                    .withOrderBookId(bestMatchingMarket.getOrderBookId())
                    .withMinMaxValue(bestMatchingMarket.getMinMaxValue())
                    .withInstrument(bestMatchingMarket.getInstrument())
                    .withNoOfItemsToMatch(bestMatchingMarket.getNoOfItemsToMatch() - noOfItemsMatched)
                    .withAllItemsMatched((bestMatchingMarket.getNoOfItemsToMatch() - noOfItemsMatched) == 0)
                    .build());

            //System.out.println("Matched with: " + marketedEntity);

            // Create initial DEAL
//            MarketDealEntity marketDealEntity = marketDealDao.insert(MarketDealEntity.builder()
//                .withInstrument(marketEntity.getInstrument())
//                .withNoOfItems(noOfItemsToMatch)
//                .withMarketId1(marketEntity.getOrderId())
//                .withMarketId2(bestMatchingMarket.getOrderId())
//                .withPrice(CalculatePrice(marketEntity, bestMatchingMarket))
//                .build());

            //System.out.println("Deal: " + marketDealEntity);

            marketOrderEntities.remove(bestMatchingMarket); // Do not use this entity the next round

            noOfItemsToMatch = itemsRemaining;

        } // While loop
    }

    private double AmountOf(double amount, Currency fromCurrency, Currency toCurrency) {
        if (fromCurrency == toCurrency) return amount;
        // NOT SAME CURRACY, CONVERT IT TO toCurrency
        return amount; // * ConvertionFactor(fromCurrency,toCurrency)
    }

    private Side OtherSide(Side side) {
        if (side == Side.BUY) return Side.SELL;
        return Side.BUY;
    }

    private MarketOrderEntity chooseEntity
            (int noOfItemsToMatch, Currency inCurrency, MarketOrderEntity current, MarketOrderEntity compareWith) {

        if (current == null) return compareWith;

        if (current.getMinMaxValue().getCurrency().equals(compareWith.getMinMaxValue().getCurrency())) { // same currency

            if (current.getNoOfItemsToMatch().equals(noOfItemsToMatch)) return current;
            if (compareWith.getNoOfItemsToMatch().equals(noOfItemsToMatch)) return compareWith;
            if (current.getNoOfItemsToMatch() >= compareWith.getNoOfItemsToMatch()) return current;
            return compareWith;

        }

        // Otherwise, always choose the same currency as in the market
        if (inCurrency.equals(current.getMinMaxValue().getCurrency())) return current;
        return compareWith;

    }

    private Money CalculatePrice (MarketOrderEntity marketEntity1, MarketOrderEntity marketEntity2) {
        // WE NEED to change the lastPrice later on
        if (marketEntity1.getSide() == Side.SELL)
            return CalculatePrice (marketEntity1.getMinMaxValue(), marketEntity1, marketEntity2);
        return CalculatePrice (marketEntity2.getMinMaxValue(), marketEntity2, marketEntity1);
    }

    private Money CalculatePrice (Money lastPrice, MarketOrderEntity seller, MarketOrderEntity buyer) {

        if (buyer.getMarketPriceType() == MarketPriceType.MARKET && seller.getMarketPriceType() == MarketPriceType.MARKET) {
            return lastPrice;
        } else if (buyer.getMarketPriceType() == MarketPriceType.MARKET && seller.getMarketPriceType() != MarketPriceType.MARKET) {
            return seller.getMinMaxValue();
        } else if (seller.getMarketPriceType() == MarketPriceType.MARKET && buyer.getMarketPriceType() != MarketPriceType.MARKET) {
            return buyer.getMinMaxValue();
        }

        // Both NOT MARKET
        if (seller.getMinMaxValue().equals(buyer.getMinMaxValue()))
            return seller.getMinMaxValue(); //Agreed price

        return lastPrice;
    }
}
