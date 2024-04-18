package com.example.classes;

import java.time.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;  
import java.text.SimpleDateFormat;  
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import org.apache.tomcat.jni.Time;
//import org.apache.tomcat.util.http.fileupload.FileUploadBase.IOFileUploadException;
//import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.fasterxml.jackson.core.JsonParseException;


public class Clients {

    public static String readClients(){
        String jsonCustomersPath = System.getProperty("user.dir");
        jsonCustomersPath = jsonCustomersPath + "/customers.json";
        try {
            JSONParser parser = new JSONParser();
            JSONArray a = (JSONArray) parser.parse(new FileReader(jsonCustomersPath));
            //List<String> clientes = new ArrayList<String>();
            for (Object o : a ){
                JSONObject customer = (JSONObject) o;
                String country = (String) customer.get("country");
                String lastname = (String) customer.get("last");
                String fecha = (String) customer.get("created_at");
                String company = (String) customer.get("company");
                Long idClient = (Long) customer.get("id");
                String mail = (String) customer.get("email");
                String name = (String) customer.get("first");
                           
                //int size = clientes.size(); 
                //String[] stringArray = clientes.toArray(new String[size]);

                System.out.println(name + " " + lastname);
                System.out.println(idClient);
                System.out.println(mail + " " + company);
                System.out.println(country + " " + fecha );      
            }
            return a.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //Object obj = parser.parse(new FileReader("C:/Users/LIGHTDESA_14/Documents/ex/ejemplo/customers.json"));
        return null;
    };

    public static void editCustomers(String country, String apellido, Date date, String fecha, String company, String nombre, String correo)
    {
        /* String country = "Mexico";
        String apellido = "Rodarte";
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String fecha = dateFormat.format(date);
        String company = "DSV";
        String nombre = "Andres";
        String correo = nombre + "." + apellido + "@test.com"; */
        try {
            //ObjectMapper mapper = new ObjectMapper();
            String key = "first"; //whatever
            String jsonCustomersPath = System.getProperty("user.dir");
            jsonCustomersPath = jsonCustomersPath + "/customers.json";
            JSONParser parser = new JSONParser();
            JSONArray a = (JSONArray) parser.parse(new FileReader(jsonCustomersPath));
            //System.out.println(a);
            //Read from file
            for (Object jo : a) {
                JSONObject customer = (JSONObject) jo;
                //JSONObject root = mapper.readValue(parser, JSONParser.class);
                String val_newer = "Andres";
                String val_older = (String) customer.get(key);
                Long indexID = (Long) customer.get("id");
                
                //Compare values
                if(!val_newer.equals(val_older))
                {
                    a.remove(customer);
                    //Update value in object
                    customer.put(key,val_newer);
                    //customer.put(key, val_older);
                    //System.out.println(customer);
                    //a.add(customer);
                    a.add(Math.toIntExact(indexID), indexID);
                    //System.out.println(a);
                    //Write into the file
                    try (FileWriter file = new FileWriter(jsonCustomersPath)) 
                    {
                        file.write(a.toJSONString());
                        System.out.println("Successfully updated json object to file...!!" + customer.toString());
                    }
                }
        }
            
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }
    }

    public static void newCustomerH(String country, String apellido, Date date, String fecha, String company, String nombre, String correo)
    {
        /* String country = "Mexico";
        String apellido = "Rodarte";
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
        String fecha = dateFormat.format(date);
        String company = "DSV";
        String nombre = "Andres";
        String correo = nombre + "." + apellido + "@test.com"; */
        
        try {
            String jsonCustomersPath = System.getProperty("user.dir");
            jsonCustomersPath = jsonCustomersPath + "/customers.json";
            JSONParser parser = new JSONParser();
            JSONArray a = (JSONArray) parser.parse(new FileReader(jsonCustomersPath));
            JSONObject customer = new JSONObject();
            customer.put("country", country);
            customer.put("last", apellido);
            customer.put("created_at", fecha);
            customer.put("company", company);
            customer.put("email", correo);
            customer.put("first", nombre);
            customer.put("id",a.size()+1);

            a.add(customer);
            try (FileWriter file = new FileWriter(jsonCustomersPath)) 
                {
                    file.write(a.toJSONString());
                    System.out.println("Successfully updated json object to file...!!" + customer.toString());
                }
                
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }
    }

    public static String delCustomerH(int idClient)
    {
        //int idClient = 5;
        try {
            String jsonCustomersPath = System.getProperty("user.dir");
            jsonCustomersPath = jsonCustomersPath + "/customers.json";
            JSONParser parser = new JSONParser();
            JSONArray a = (JSONArray) parser.parse(new FileReader(jsonCustomersPath));

            a.remove(idClient);
            try (FileWriter file = new FileWriter(jsonCustomersPath))
                {
                    file.write(a.toJSONString());
                    System.out.println("Successfully updated json object to file...!!");
                }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }
        return "Successfully updated json object to file..!!";
    }

}
