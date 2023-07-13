package com.example.back.controller;

import com.example.back.config.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/http")
public class HttpController {

	@PostMapping("/trouble")
	public ResponseEntity<?> getTrouble(@Validated @RequestBody  Map<String,Object> params) {
        Map<String, Object> response = new HashMap<>();
        String info = (String) params.get("info");
        if(info.equals("wakeup")){
            try {
                WebSocket webSocket = WebSocket.getWebSocketById("okok");
                if (webSocket != null) {
                    webSocket.sendMessageToId("okok","wakeup!");
                }else{
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("没连接！！！！！");
                }
                return ResponseEntity.ok("wakeup处理成功");
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("wakeup处理失败");
            }
        }else if(info.equals("focus")){
            try {
                WebSocket webSocket = WebSocket.getWebSocketById("okok");
                if (webSocket != null) {
                    webSocket.sendMessageToId("okok","focus!");
                }else{
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("没连接！！！！！");
                }
                return ResponseEntity.ok("focus处理成功");
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("focus处理失败");
            }
        }else if(info.equals("call")){
            try {
                WebSocket webSocket = WebSocket.getWebSocketById("okok");
                if (webSocket != null) {
                    webSocket.sendMessageToId("okok","call!");
                }else{
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("没连接！！！！！");
                }
                return ResponseEntity.ok("call处理成功");
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("call处理失败");
            }
        }
        return ResponseEntity.ok("finefine");
    }

}
