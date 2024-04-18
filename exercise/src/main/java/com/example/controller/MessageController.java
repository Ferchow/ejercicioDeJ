package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;  
import java.util.Date;

import com.example.classes.Clients;

@RestController
@RequestMapping("~/api/messages")
public class MessageController {
     @RequestMapping("/hello")
     public String hello() {
        return "hello";
     }

     @GetMapping("/Customers")
     public String customers(){
      return Clients.readClients();
     }

     @GetMapping("/deleteClient")
     public String eliminarCliente(@RequestParam int cliente){
      //model.addAttribute("eventName", "FIFA 2018");
      Clients.delCustomerH(cliente);
      return "Usuario eliminado";
     }
     @GetMapping("/updateClient")
     public String actualizarCliente(@RequestParam int cliente){
      String country = "Mexico";
      String apellido = "Rodarte";
      Date date = Calendar.getInstance().getTime();
      DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
      String fecha = dateFormat.format(date);
      String company = "DSV";
      String nombre = "Andres";
      String correo = nombre + "." + apellido + "@test.com";

      Clients.editCustomers(country, apellido, date, fecha, company, nombre, correo);
      return "cliente editado";
     }

     @GetMapping("/addClient")
     public String nuevoCliente(){
      String country = "Mexico";
      String apellido = "Rodarte";
      Date date = Calendar.getInstance().getTime();
      DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
      String fecha = dateFormat.format(date);
      String company = "DSV";
      String nombre = "Andres";
      String correo = nombre + "." + apellido + "@test.com";
      Clients.newCustomerH(country, apellido, date, fecha, company, nombre, correo);
      return "Cliente agregado";
     }
   }
