package com.edu.fateczl.ticketsales.model;

/**
 * @author Adriano M Sanchez
 */
public class VipTicket extends Ticket{

    private String buyerFunction;

    public VipTicket(String id, float value, String buyerFunction) {
        super(id, value);
        this.buyerFunction = buyerFunction;
    }

    public String getBuyerFunction() {
        return buyerFunction;
    }

    public void setBuyerFunction(String buyerFunction) {
        this.buyerFunction = buyerFunction;
    }

    @Override
    public float finalValue(float convenienceFee){
        float v = super.finalValue(convenienceFee);
        return v + v * 0.18f;
    }
}
