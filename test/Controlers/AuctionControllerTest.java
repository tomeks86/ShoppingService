package Controlers;


import Databases.AuctionDataBase;
import models.Auction;
import models.User;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


public class AuctionControllerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    public AuctionControler testObject;
    private AuctionDataBase auctionDataBase;
    private ArrayList<Auction> auctionArrayList;
    private User owner = new User("login", "password");

    @Before
    public void deleteAll() {
        try {
            Files.delete(Paths.get("Auction.bin"));                 //TODO need to delete file before every test to clean database, we need to change construction of auctionDataBase to add possibility to put new directory for new file
        } catch (IOException e) {
            e.printStackTrace();
        }

        testObject = new AuctionControler();
        auctionDataBase = new AuctionDataBase();
        auctionArrayList = new ArrayList<>();
    }


    @Test
    public void sholudReturnTrueIfAuctionAddedToArrayListOfAuctions() {
        Auction auction = new Auction("some test auction", "Some test Title", new BigDecimal(41.0), owner, 14, 6);
        testObject.addAuction(auctionDataBase, auction);
        auctionArrayList.add(auction);
        Assert.assertEquals(auctionDataBase.getListOfAllAuctions(), auctionArrayList);
    }

    @Test
    public void shouldReturnTrueIfAuctionRemovedFromArrayListOfAuction() {
        Auction auction = new Auction("some test auction", "Some test Title", new BigDecimal(42.0), owner, 14, 6);
        Auction auction1 = new Auction("some test auction", "Some test Title", new BigDecimal(43.0), owner, 15, 6);
        Auction auction2 = new Auction("some test auction", "Some test Title", new BigDecimal(44.0), owner, 18, 5);

        testObject.addAuction(auctionDataBase, auction);
        testObject.addAuction(auctionDataBase, auction1);
        testObject.addAuction(auctionDataBase, auction2);
        auctionArrayList.add(auction);
        auctionArrayList.add(auction1);
        auctionArrayList.add(auction2);

        testObject.removeAuction(auctionDataBase, auction1, owner);
        auctionArrayList.get(1).setActive(false);

        Assert.assertEquals(auctionDataBase.getListOfAllAuctions().get(1).isActive(), auctionArrayList.get(1).isActive());
    }

    @Test
    public void shouldReturnTrueIfAccessToRemoveAuctionGranted() {
        Auction auction = new Auction("some test auction", "Some test Title", new BigDecimal(42.0), owner, 14, 6);
        testObject.addAuction(auctionDataBase, auction);

        Auction auction1 = testObject.checkingAccesToRemoveAuction(owner, auctionDataBase, 14);

        Assert.assertEquals(auction, auction1);
    }

    @Test
    public void shouldThrowNullPointerExceptionWhenPassWrongIdToRemoveAuction() {
        Auction auction = new Auction("some test auction", "Some test Title", new BigDecimal(42.0), owner, 14, 6);
        testObject.addAuction(auctionDataBase, auction);

        thrown.expect(NullPointerException.class);
        thrown.expectMessage("There is no such auction to remove! ");

        testObject.checkingAccesToRemoveAuction(owner, auctionDataBase, 45);
    }

    @Test
    public void shouldThrowNullPointerExceptionWhenPassWrongUserToRemoveAuction() {
        Auction auction = new Auction("some test auction", "Some test Title", new BigDecimal(42.0), owner, 14, 6);
        testObject.addAuction(auctionDataBase, auction);

        User testUser = new User("testLogin", "testPassword");

        thrown.expect(NullPointerException.class);
        thrown.expectMessage("There is no such auction to remove! ");

        testObject.checkingAccesToRemoveAuction(testUser, auctionDataBase, 14);
    }

    @Test
    public void shouldThrowNullPointerExceptionWhenTryToRemoveAlreadyDeletedAuction() {
        Auction auction = new Auction("some test auction", "Some test Title", new BigDecimal(42.0), owner, 14, 6);
        testObject.addAuction(auctionDataBase, auction);
        auctionDataBase.removeAuction(auction);

        thrown.expect(NullPointerException.class);
        thrown.expectMessage("There is no such auction to remove! ");

        testObject.checkingAccesToRemoveAuction(owner, auctionDataBase, 14);
    }

    @Test
    public void shouldReturnTrueIfAccessToBidAuctionGranted() {
        Auction auction = new Auction("some test auction", "Some test Title", new BigDecimal(42.0), owner, 14, 6);
        testObject.addAuction(auctionDataBase, auction);

        User testUser = new User("testLogin", "testPassword");

        Auction auction1 = testObject.checkAccessToBidAuction(auctionDataBase, 14, testUser);

        Assert.assertEquals(auction, auction1);
    }

    @Test
    public void shouldThrowNullPointerExceptionWhenPassWrongIdToBidAuction() {
        Auction auction = new Auction("some test auction", "Some test Title", new BigDecimal(42.0), owner, 14, 6);
        testObject.addAuction(auctionDataBase, auction);

        User testUser = new User("testLogin", "testPassword");

        thrown.expect(NullPointerException.class);
        thrown.expectMessage("There is no such auction to bid! ");

        testObject.checkAccessToBidAuction(auctionDataBase, 48, testUser);
    }

    @Test
    public void shouldThrowNullPointerExceptionWhenPassOwnerToBidAuction() {
        Auction auction = new Auction("some test auction", "Some test Title", new BigDecimal(42.0), owner, 14, 6);
        testObject.addAuction(auctionDataBase, auction);

        User testUser = new User("testLogin", "testPassword");

        thrown.expect(NullPointerException.class);
        thrown.expectMessage("There is no such auction to bid! ");

        testObject.checkAccessToBidAuction(auctionDataBase, 14, owner);
    }

    @Test
    public void shouldThrowNullPointerExceptionWhenPassActualWinnerToBidAuction() {
        Auction auction = new Auction("some test auction", "Some test Title", new BigDecimal(42.0), owner, 14, 6);
        testObject.addAuction(auctionDataBase, auction);

        User testUser = new User("testLogin", "testPassword");

        thrown.expect(NullPointerException.class);
        thrown.expectMessage("There is no such auction to bid! ");

        auction.bidPrice(new BigDecimal(45.0));
        auction.setActualWinnerOfAuction(testUser);
        testObject.checkAccessToBidAuction(auctionDataBase, 14, testUser);
    }

    @Test
    public void shouldThrowNullPointerExceptionWhenTryToBidDeletedAuction() {
        Auction auction = new Auction("some test auction", "Some test Title", new BigDecimal(42.0), owner, 14, 6);
        testObject.addAuction(auctionDataBase, auction);
        auctionDataBase.removeAuction(auction);

        User testUser = new User("testLogin", "testPassword");

        thrown.expect(NullPointerException.class);
        thrown.expectMessage("There is no such auction to bid! ");

        testObject.checkAccessToBidAuction(auctionDataBase, 14, testUser);
    }

    @Test
    public void shouldReturnTrueIfUserExpiredAuctionsFilterWell(){
        Auction auction = new Auction("some test auction", "Some test Title", new BigDecimal(42.0), owner, 14, 6);
        Auction auction1 = new Auction("some test auction", "Some test Title", new BigDecimal(42.0), owner, 14, 6);
        Auction auction2 = new Auction("some test auction", "Some test Title", new BigDecimal(42.0), owner, 14, 6);
        testObject.addAuction(auctionDataBase,auction);
        testObject.addAuction(auctionDataBase,auction1);
        testObject.addAuction(auctionDataBase,auction2);
        testObject.removeAuction(auctionDataBase,auction,owner);
        testObject.removeAuction(auctionDataBase,auction1,owner);

        auction.setActive(false);
        auction1.setActive(false);

        auctionArrayList.add(auction);
        auctionArrayList.add(auction1);

        ArrayList<Auction> testList = testObject.getUserExpiredAuctions(owner, auctionDataBase);

        Assert.assertEquals(testList, auctionArrayList);

    }


}
