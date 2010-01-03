/*
 * This file is part of ###PROJECT_NAME###
 *
 * Copyright (C) 2009 Fundación para o Fomento da Calidade Industrial e
 *                    Desenvolvemento Tecnolóxico de Galicia
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.navalplanner.ws.common.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.navalplanner.ws.common.api.ConstraintViolationDTO;
import org.navalplanner.ws.common.api.InstanceConstraintViolationsDTO;

/**
 * Converter for constraint violations.
 *
 * @author Fernando Bellas Permuy <fbellas@udc.es>
 */
public class ConstraintViolationConverter {

    private final static String CHECK_CONSTRAINT_METHOD_PREFIX =
        "checkConstraint";

    private ConstraintViolationConverter() {}

    public final static ConstraintViolationDTO toDTO(
        InvalidValue invalidValue) {

        String fieldName = null;

        if ( (invalidValue.getPropertyName() != null) &&
             (!invalidValue.getPropertyName().
                 startsWith(CHECK_CONSTRAINT_METHOD_PREFIX))) {
            final String rootObjectClassName = invalidValue.getRootBean()
                    .getClass().getSimpleName();
            final String propertyPath = invalidValue.getPropertyPath();
            fieldName = rootObjectClassName + "::" + propertyPath;
        }

        return new ConstraintViolationDTO(fieldName,
            invalidValue.getMessage());

    }

    public final static InstanceConstraintViolationsDTO toDTO(String instanceId,
        InvalidValue[] invalidValues) {

        List<ConstraintViolationDTO> constraintViolationDTOs =
            new ArrayList<ConstraintViolationDTO>();

        for (InvalidValue i : invalidValues) {
            constraintViolationDTOs.add(toDTO(i));
        }

        return new InstanceConstraintViolationsDTO(instanceId,
            constraintViolationDTOs);

    }

}
