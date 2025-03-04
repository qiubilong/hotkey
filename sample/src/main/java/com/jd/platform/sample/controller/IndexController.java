package com.jd.platform.sample.controller;

import com.jd.platform.hotkey.client.callback.JdHotKeyStore;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/index")
public class IndexController {


    @RequestMapping("/get/{key}")
    public Object get(@PathVariable String key) {
        String cacheKey = "skuId_" + key;
        if (JdHotKeyStore.isHotKey(cacheKey)) {/* 1、热key,如果不是热key则上报 */
            System.out.println("hotkey:"+ cacheKey);
            //注意是get，不是getValue。getValue会获取并上报，get是纯粹的本地获取
            Object skuInfo = JdHotKeyStore.get(cacheKey);/* 1.1 热key，读取JVM缓存 */
            if (skuInfo == null) {
                Object theSkuInfo = "123" + "[" + key + "]" + key;
                JdHotKeyStore.smartSet(cacheKey, theSkuInfo); /* 1.2 值不存在，设置JVM缓存 */
                return theSkuInfo;
            } else {
                //使用缓存好的value即可
                return skuInfo;
            }
            //["skuId__1","skuId__2","skuId__3"]
        } else { /* 2、非热key */
            System.out.println("not hot:"+ cacheKey);
            return "123" + "[" + key + "]" + key;
            //从redis当中获取数据
            //mysql当中获取数据
        }
    }

    @RequestMapping("/get/info")
    public Object getGoodsInfo(){
        //127.0.0.1/get/info/1
        //key skuId__1
        // /get/info/1
        //127.0.0.1
        String cacheKey = "as_" + 1;
        //
        if (JdHotKeyStore.isHotKey(cacheKey)) {//限流
            System.out.println("hot:"+ cacheKey);
            return "访问次数太多请稍后再试！";
        } else {
            System.out.println("not hot:"+ cacheKey);
            return "ok";
        }
    }
}
