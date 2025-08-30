package hu.tomi.logistic.service;


import hu.tomi.logistic.model.Address;
import hu.tomi.logistic.repository.AddressRepository;
import hu.tomi.logistic.specification.AddressSpecification;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Transactional(readOnly = true)
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Address getAddressById(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found with id: " + id));
    }

    @Transactional
    public Address createAddress(Address address) {
        return addressRepository.save(address);
    }

    @Transactional
    public Address updateAddress(Long id, Address updated) {
        Address existing = addressRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found with id: " + id));

        existing.setCountryCode(updated.getCountryCode());
        existing.setCity(updated.getCity());
        existing.setZipCode(updated.getZipCode());
        existing.setStreet(updated.getStreet());
        existing.setHouseNumber(updated.getHouseNumber());
        existing.setLatitude(updated.getLatitude());
        existing.setLongitude(updated.getLongitude());

        return addressRepository.save(existing);
    }

    @Transactional
    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<Address> searchAddresses(Address filter, Pageable pageable) {
        Specification<Address> spec = AddressSpecification.search(filter);
        return addressRepository.findAll(spec, pageable);
    }
}
