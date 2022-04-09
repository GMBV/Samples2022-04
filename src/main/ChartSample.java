import com.alibaba.fastjson.JSON;

class ChartSample {

    public static void main(String[] args) {

        User user = new User();
        user.id = 1;
        user.name = "John";
        user.email= "test@test.com";

        String jsonUser = JSON.toJSONString(user);

        System.out.println(jsonUser);
    }

}