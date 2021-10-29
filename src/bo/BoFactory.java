package bo;

import bo.custom.impl.CustomerBOImpl;
import bo.custom.impl.ItemBOImpl;
import bo.custom.impl.ManageOrderBOImpl;
import bo.custom.impl.PlaceOrderBOImpl;

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
            default:
                return null;
        }
    }

    public enum BoTypes{
        PLACE_ORDER,CUSTOMER,ITEM,MANAGE_ORDERS
    }

}
