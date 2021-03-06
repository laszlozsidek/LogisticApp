package hu.webuni.logisticApp.lzsidek.service;

import hu.webuni.logisticApp.lzsidek.model.Milestone;
import hu.webuni.logisticApp.lzsidek.repository.MilestoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MilestoneService {

    @Autowired
    MilestoneRepository milestoneRepository;

    public Optional<Milestone> getMilestoneById(Long id) {
        return milestoneRepository.findById(id);
    }

    @Transactional
    public Milestone saveMilestone(Milestone milestone) {
        return milestoneRepository.save(milestone);
    }

    public List<Milestone> getMilestones() {
        return milestoneRepository.findAll();
    }
}