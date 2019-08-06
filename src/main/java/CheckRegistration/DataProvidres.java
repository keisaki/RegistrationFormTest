package CheckRegistration;

import org.testng.annotations.DataProvider;

import java.util.HashMap;

public class DataProvidres {
    @DataProvider
    public static Object[][] Mail_check_false_data() {
        return new Object[][] { { "heh" }, { "heh@" } , { "heh@mai" },{"@."},{"@mail.ru"},{"ааа@mail.ru"},{"ффф@ффф.ru"},{"ааа@ффф.фф"}};
    }
    @DataProvider
    public static Object [][] Mail_check_true_data() {
        return new Object[][]{{"heh@gmail.com"},{"hefgdffgdgkasfh@mail.ru"},{"test312@mail.ru"},{"test6452@mail.ru"}};
    }
    @DataProvider
    public static Object [][] user_information_check_true_data() {
        HashMap<String,String> data = new HashMap<String, String>();
        data.put("customer_firstname","Ivan");
        data.put("customer_lastname","Ivanov");
        data.put("passwd","password");
        data.put("company","My_comp");
        data.put("address1","my,adr");
        data.put("city","mycity");
        data.put("postcode","00000");
        data.put("phone","8909000");
        data.put("phone_mobile","8909000");
        data.put("alias","anotherAdr");
        HashMap<String,Integer> data_sl = new HashMap<String, Integer>();
        data_sl.put("id_state",1);
        data_sl.put("id_country",1);
        data_sl.put("days",1);
        data_sl.put("months",1);
        data_sl.put("years",1);
        HashMap<String,Boolean> data_ck = new HashMap<String, Boolean>();
        data_ck.put("newsletter",true);
        data_ck.put("optin",true);

        return new Object[][]{{data,data_sl,data_ck}};
    }

}
