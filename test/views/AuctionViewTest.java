package views;


import models.Auction;
import models.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


import java.math.BigDecimal;
import java.util.ArrayList;

public class AuctionViewTest {

    AuctionView auctionView = new AuctionView();
    Auction auction;
    User user;

    @Before
    public void setUp() {
        auction = new Auction("some test auction", "Some test Title", new BigDecimal(41.0), new User("llllll", "ksksksks"), 14, 6);
        user = new User("login", "password");
    }


    @Test
    public void shouldReturtTrueWhenToLowOffertPrints() {
        String message = auctionView.printTooLowOffer(auction);

        Assert.assertEquals("Your offer was too low, you need to give more than " + auction.getPrice(), message);
    }

    @Test
    public void shouldReturnTrueIfprintsErrorWhenWrongCategoryChosen() {
        String message = auctionView.printsErrorWhenWrongCategoryChosen();

        Assert.assertEquals("There is no such category ! ", message);
    }

    @Test
    public void shouldReturnTrueIfShowComunicatWhenAuctionAddedIsValid() {
        String message = auctionView.showComunicatWhenAuctionAdded();

        Assert.assertEquals("Auction added! ", message);
    }

    @Test
    public void shouldReturnTrueIfShowComunicatWhenAuctionNotAddedIsValid() {
        String message = auctionView.showComunicatWhenAuctionNotAdded();

        Assert.assertEquals("Problem occurred while adding auction, auction not added", message);

    }

    @Test
    public void shouldReturnTrueIfshowComunicatWhenAuctionRemovedIsValid() {
        String message = auctionView.showComunicatWhenAuctionRemoved();

        Assert.assertEquals("Auction removed", message);
    }

    @Test
    public void shouldReturnTrueIfshowComunicatWhenAuctionNotRemovedIsValid() {
        String message = auctionView.showComunicatWhenAuctionNotRemoved();

        Assert.assertEquals("Problems occurred, auction not removed! Try again. ", message);
    }

    @Test
    public void shouldReturnTrueIfshowComunicatWhenFileNotSavedIsValid() {
        String message = auctionView.showComunicatWhenFileNotSaved();

        Assert.assertEquals("Cannot save file !", message);
    }


    @Test
    public void shouldReturnTrueIfprintCongratulationMessageisValid() {
        String message = auctionView.printCongratulationMessage(auction, user);

        Assert.assertEquals("Congratulations " + user.getUserName() + "! You've just bouhgt: " + auction.toString(), message);

    }

    @Test
    public void shouldReturnTrueIfprintCurrentOfferMethodIsValid() {
        String message = auctionView.printCurrentOffer(auction);

        Assert.assertEquals("You've made a bid, the current for: " + auction.toString(), message);
    }

    @Test
    public void shouldReturnTrueIfprintErrorWhenWrongAuctionChosenToBidIsValid() {
        String message = auctionView.printErrorWhenWrongAuctionChosenToBid();

        Assert.assertEquals("There is no such auction, bid impossible, you are owner of auction or already have highest bid", message);
    }

    @Test
    public void shouldReturnTrueIfprintUserExpiredAuctionsIsValid() {
        Auction auction1 = new Auction("somedsdtion", "Some test Title", new BigDecimal(45.0), new User("lasdasdl", "kssdsaksksks"), 15, 6);

        auction.setActive(false);
        auction1.bidPrice(new BigDecimal(80));
        auction1.bidPrice(new BigDecimal(81));
        auction1.bidPrice(new BigDecimal(85));
        auction1.setActualWinnerOfAuction(new User("winer", "haslo"));

        ArrayList<Auction> list = new ArrayList<>();
        list.add(auction);
        list.add(auction1);
        String auctions = auctionView.printUserExpiredAuctions(list);
        String expected = auction.toString() + ": (removed by user)" +
                "\n" + auction1.toString() + ": bought by user: " + auction1.getBuyer().getUserName();

        Assert.assertEquals(expected,auctions);
    }


}
