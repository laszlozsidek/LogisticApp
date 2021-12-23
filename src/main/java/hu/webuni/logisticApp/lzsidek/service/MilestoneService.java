package hu.webuni.logisticApp.lzsidek.service;

import hu.webuni.logisticApp.lzsidek.model.Milestone;
import hu.webuni.logisticApp.lzsidek.repository.MilestoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MilestoneService {

    @Autowired
    MilestoneRepository milestoneRepository;

    public Optional<Milestone> getMilestoneById(Long id) {
        return milestoneRepository.findById(id);
    }
}