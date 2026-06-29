CREATE TABLE colaborador_sucursal (
    colaborador_id INT NOT NULL,
    sucursal_id INT NOT NULL,
    PRIMARY KEY (colaborador_id, sucursal_id),
    CONSTRAINT fk_colab_suc_colaborador FOREIGN KEY (colaborador_id) 
        REFERENCES colaboradores (id) ON DELETE CASCADE,
    CONSTRAINT fk_colab_suc_sucursal FOREIGN KEY (sucursal_id) 
        REFERENCES sucursales (id) ON DELETE CASCADE
);

INSERT INTO colaborador_sucursal (colaborador_id, sucursal_id)
SELECT id, id_sucursal_fk FROM colaboradores WHERE id_sucursal_fk IS NOT NULL;

ALTER TABLE colaboradores DROP FOREIGN KEY fk_colaborador_sucursal;
ALTER TABLE colaboradores DROP COLUMN id_sucursal_fk;