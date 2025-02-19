package com.mobiversa.payment.dto;

import java.util.Comparator;

public class MerchantComparator implements Comparator<MerchantVolumeData> {
    @Override
    public int compare(MerchantVolumeData o1, MerchantVolumeData o2) {
        return o1.getId().compareTo(o2.getId());
    }
} 
