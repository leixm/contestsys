import com.app.tools.MD5Util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class testAdd {
    public static void main(String[] args) {
     /*  List list = new ArrayList();
       list.add(1);
       list.add(2);
       list.add(0,3);
       System.out.println(list);*/
        String answer = "§§§§§§§§§";
        StringBuilder simsolutionSB = new StringBuilder();
        String[] simsolutionArr = answer.split("§§§");
        System.out.println("你好");
        if(simsolutionArr.length > 0) {
            for(String arr : simsolutionArr){
                try{
                    System.out.println("arr---------"+arr);
                    simsolutionSB.append(arr);
                    simsolutionSB.append(" 、");
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            String simsolutionContent = simsolutionSB.substring(0,simsolutionSB.length()-1);
            System.out.println(simsolutionContent);

        }
    }


}
