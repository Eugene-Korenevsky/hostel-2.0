package com.example.hostel1.servicies.logic;

import com.example.hostel1.entities.Description;
import com.example.hostel1.repositories.DescriptionRepository;
import com.example.hostel1.servicies.DescriptionService;
import org.springframework.stereotype.Service;

@Service
public class DescriptionServiceImpl extends GenericServiceImpl<Description> implements DescriptionService {
    private DescriptionRepository descriptionRepository;

    public DescriptionServiceImpl(DescriptionRepository descriptionRepository) {
        super(descriptionRepository, Description.class);
        this.descriptionRepository = descriptionRepository;
    }
}
