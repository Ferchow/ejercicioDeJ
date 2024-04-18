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

import com.example.classes.Rooms;


@RestController
public class HotelRController {
    @GetMapping("/showRooms")
    public String mostrarCartos(){
        Rooms.readHotelR();
        return Rooms.readHotelR();
    }

    @GetMapping("/deleteRoom")
    public String eliminarCuarto(){
        Rooms.delRoom();
        return "Cuarto eliminado";
    }

    @GetMapping("/updateRoom")
    public String actualizarCuarto(){
        Rooms.editHotelR();
        return "Cuarto actualizado";
    }

    @GetMapping("/newRoom")
    public String nuevoCuarto(){
        Rooms.newRoom();
        return "Cuarto añadido";
    }
}
