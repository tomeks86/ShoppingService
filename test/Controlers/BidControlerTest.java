package Controlers;

import Databases.AuctionDataBase;
import models.Auction;
import models.User;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BidControlerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    public BidControler testBidControler = new BidControler();
    AuctionDataBase auctionDataBase;

    @Before
    public void setUp() {
        auctionDataBase = new AuctionDataBase();
        User owner = new User("login", "password");
        Auction auction = new Auction("description", "title", 45.0, owner, 4, 5);
        auctionDataBase.addAuction(auction);
    }

    @Test
    public void shouldReturnFalseWhenSuccessfullyBidAuction() {
        User buyer = new User("login1", "password1");
        assertFalse(testBidControler.bidAuction(buyer,
                auctionDataBase.getListOfAllAuction().get(auctionDataBase.getListOfAllAuction().size() - 1), 46.0, auctionDataBase));
    }

    @Test
    public void shouldReturnTrueWhenSuccessfullyWinAuction() {
        User buyer1 = new User("User1", "pass1");
        User buyer2 = new User("User2", "pass2");
        User buyer3 = new User("User3", "pass3");
        testBidControler.bidAuction(buyer1,
                auctionDataBase.getListOfAllAuction().get(auctionDataBase.getListOfAllAuction().size() - 1), 46.0, auctionDataBase);

        testBidControler.bidAuction(buyer2,
                auctionDataBase.getListOfAllAuction().get(auctionDataBase.getListOfAllAuction().size() - 1), 47.0, auctionDataBase);

        assertTrue(testBidControler.bidAuction(buyer3,
                auctionDataBase.getListOfAllAuction().get(auctionDataBase.getListOfAllAuction().size() - 1), 48.0, auctionDataBase));
    }

    @Test
    public void shouldThrowIllegalStateExceptionWhenPriceOfBidIsTooLow() {
        User buyer = new User("Username", "password");

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Price is too low");

        testBidControler.bidAuction(buyer,
                auctionDataBase.getListOfAllAuction().get(auctionDataBase.getListOfAllAuction().size() - 1), 44.0, auctionDataBase);

    }

    @Test
    public void shouldThrowIllegalStateExceptionWhenPriceOfBidIsTheSameAsPriceOfAuction() {
        User buyer = new User("Username", "password");

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Price is too low");

        testBidControler.bidAuction(buyer,
                auctionDataBase.getListOfAllAuction().get(auctionDataBase.getListOfAllAuction().size() - 1), 45.0, auctionDataBase);

    }

}
