package hu.tomi.logistic.service;

import hu.tomi.logistic.model.Address;
import hu.tomi.logistic.model.Milestone;
import hu.tomi.logistic.repository.AddressRepository;
import hu.tomi.logistic.repository.MilestoneRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MilestoneService {

    private final MilestoneRepository milestoneRepository;
    private final AddressRepository addressRepository;

    public MilestoneService(MilestoneRepository milestoneRepository,
                            AddressRepository addressRepository) {
        this.milestoneRepository = milestoneRepository;
        this.addressRepository = addressRepository;
    }
    
    @Transactional(readOnly = true)
    public List<Milestone> getAll() {
        return milestoneRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Milestone getById(Long id) {
        return milestoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Milestone not found with id: " + id));
    }
    
    @Transactional
    public Milestone save(Milestone milestone, Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found with id: " + addressId));

        milestone.setAddress(address);
        return milestoneRepository.save(milestone);
    }
    
    @Transactional
    public void delete(Long id) {
        milestoneRepository.deleteById(id);
    }
}
