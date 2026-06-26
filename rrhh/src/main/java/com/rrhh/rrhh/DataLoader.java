package com.rrhh.rrhh;

import com.rrhh.rrhh.model.Area;
import com.rrhh.rrhh.model.TipoContrato;
import com.rrhh.rrhh.repository.AreaRepository;
import com.rrhh.rrhh.repository.TipoContratoRepository;

import net.datafaker.Faker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Random;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private TipoContratoRepository tipoContratoRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            Area area = new Area();
            area.setNombre(faker.company().industry());
            area.setDescripcion("Área encargada de " + faker.company().profession());
            areaRepository.save(area);
        }

        for (int i = 0; i < 5; i++) {
            TipoContrato tipoContrato = new TipoContrato();
            tipoContrato.setNombre(
                    faker.options().option("Contrato indefinido", "Contrato plazo fijo", "Contrato honorarios"));
            tipoContrato.setDescripcion("Contrato generado para pruebas");
            tipoContrato.setModalidad(faker.options().option("Presencial", "Remoto", "Híbrido"));
            tipoContrato.setJornada(faker.options().option("Completa", "Parcial", "Turnos"));
            tipoContrato.setFechaCreacion(LocalDate.now().minusDays(random.nextInt(30)));
            tipoContratoRepository.save(tipoContrato);
        }
    }
}
