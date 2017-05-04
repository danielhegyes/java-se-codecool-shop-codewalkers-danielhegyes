package com.codecool.shop.model;



import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductDaoMem;

import javax.sound.sampled.Line;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by tahin on 2017.05.02..
 */
public class Order {

    private static int currentId = 0;

    private int id;
    private Set<Lineitem> orderLines = new LinkedHashSet<>();
    private float total = 0;
    private static Order instance = null;


    private Order(){
        this.id = currentId;
        currentId++;
    }

    public static Order getInstance() {
        if (instance == null) {
            instance = new Order();
        }
        return instance;
    }

    public Set<Lineitem> getOrderLines() {
        return orderLines;
    }

    public Lineitem getLine(int id)  {
        return orderLines.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }





    public boolean addLine(Lineitem line){
        for (Lineitem l: orderLines){
            if (line.getProduct().equals(l.getProduct())){
                return false;
            }
        }
        this.orderLines.add(line);
        this.total += line.getLinePrice();
        return true;
    }

    public void addItem(int id){
        ProductDao productDataStore = ProductDaoMem.getInstance();
        Lineitem line = new Lineitem(productDataStore.find(id));
        instance.addLine(line);
    }

    public int getId() {
        return id;
    }

    public float getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderLines=" + orderLines +
                ", total=" + total +
                '}';
    }
}