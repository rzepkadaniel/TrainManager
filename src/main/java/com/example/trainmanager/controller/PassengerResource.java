package com.example.trainmanager.controller;

import com.example.trainmanager.model.Passenger;
import com.example.trainmanager.repository.PassengerRepository;
import org.apache.tomcat.util.http.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
public class PassengerResource {

    private final Logger log = LoggerFactory.getLogger(PassengerResource.class);

    private static final String ENTITY_NAME = "passenger";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PassengerRepository passengerRepository;

    public PassengerResource(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }


    /**
     * {@code POST  /passengers} : Create a new passenger.
     *
     * @param passenger the passenger to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new passenger, or with status {@code 400 (Bad Request)} if the passenger has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/passengers")
    public ResponseEntity<Passenger> createPassenger(@RequestBody Passenger passenger) throws URISyntaxException {
        log.debug("REST request to save Passenger : {}", passenger);
        if (passenger.getId() != null) {
            throw new BadRequestAlertException("A new passenger cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Passenger result = passengerRepository.save(passenger);
        return ResponseEntity.created(new URI("/api/passengers/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /passengers} : Updates an existing passenger.
     *
     * @param passenger the passenger to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated passenger,
     * or with status {@code 400 (Bad Request)} if the passenger is not valid,
     * or with status {@code 500 (Internal Server Error)} if the passenger couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/passengers")
    public ResponseEntity<Passenger> updatePassenger(@RequestBody Passenger passenger) throws URISyntaxException {
        log.debug("REST request to update Passenger : {}", passenger);
        if (passenger.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Passenger result = passengerRepository.save(passenger);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, passenger.getId().toString()))
                .body(result);
    }

    /**
     * {@code GET  /passengers} : get all the passengers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of passengers in body.
     */
    @GetMapping("/passengers")
    public List<Passenger> getAllPassengers() {
        log.debug("REST request to get all Passengers");
        return passengerRepository.findAll();
    }

    /**
     * {@code GET  /passengers/:id} : get the "id" passenger.
     *
     * @param id the id of the passenger to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the passenger, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/passengers/{id}")
    public ResponseEntity<Passenger> getPassenger(@PathVariable Long id) {
        log.debug("REST request to get Passenger : {}", id);
        Optional<Passenger> passenger = passengerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(passenger);
    }

    /**
     * {@code DELETE  /passengers/:id} : delete the "id" passenger.
     *
     * @param id the id of the passenger to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/passengers/{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
        log.debug("REST request to delete Passenger : {}", id);
        passengerRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }



}
