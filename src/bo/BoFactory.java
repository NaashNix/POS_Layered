package bo;

import bo.custom.impl.*;

public class BoFactory {
    private static BoFactory boFactory;

    private BoFactory(){

    }

    public static BoFactory getBOFactory() {
        if (boFactory == null) {
            boFactory = new BoFactory();
        }
        return boFactory;
    }

    public SuperBO getBO(BoTypes types) {
        switch (types) {
            case PLACE_ORDER:
                return new PlaceOrderBOImpl();
            case CUSTOMER:
                return new CustomerBOImpl();
            case ITEM:
                return new ItemBOImpl();
            case MANAGE_ORDERS:
                return new ManageOrderBOImpl();
            case LOGIN:
                return new LoginBOImpl();
            default:
                return null;
        }
    }

    public enum BoTypes{
        PLACE_ORDER,CUSTOMER,ITEM,MANAGE_ORDERS, LOGIN
    }

}
