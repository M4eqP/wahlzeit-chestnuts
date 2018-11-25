/*
 * Copyright (c) 2018 M4eqP@users.noreply.github.com
 * This file is part of the Wahlzeit photo rating application.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */

package org.wahlzeit.model;

/**
 * Implements common functionality shared between different types of coordinates.
 */
public abstract class AbstractCoordinate implements Coordinate {
    /**
     * "Convert" object into CartesianCoordinate
     *
     * @methodtype conversion
     */
    @Override
    abstract public SphericCoordinate asSphericCoordinate();

    /**
     * "Convert" object into SphericCoordinate
     *
     * @methodtype conversion
     */
    @Override
    abstract public CartesianCoordinate asCartesianCoordinate();

    /**
     * @methodtype boolean-query
     * Checks whether two coordinates are equal.
     * @param other another CartesianCoordinate
     * @return if other CartesianCoordinate is equal with this one
     */
    @Override
    public boolean isEqual(Coordinate other) {
        // "normalize" both current and other object to cartesian coordinates, and compare the values
        // need a CartesianCoordinate to be able to access x, y, z values
        // also, the function call will implicitly reinterpret the original values into Cartesian coordinates
        CartesianCoordinate cartesianThis = asCartesianCoordinate();
        CartesianCoordinate cartesianOther = other.asCartesianCoordinate();

        double threshold = 1e-5;

        double xDiff = Math.abs(cartesianOther.getX() - cartesianThis.getX());
        double yDiff = Math.abs(cartesianOther.getY() - cartesianThis.getY());
        double zDiff = Math.abs(cartesianOther.getZ() - cartesianThis.getZ());

        return (xDiff + yDiff + zDiff) < threshold;
    }

    /**
     * @methodtype boolean-query
     * Checks whether two coordinates are equal.
     * @param other another CartesianCoordinate
     * @return true if both are equal, false otherwise
     */
    public boolean equals(Coordinate other) {
        return isEqual(other);
    }

    /**
     * Calculates direct cartesian distance between two cartesian coordinates.
     * Will convert other coordinate automatically, if necessary.
     *
     * @methodytpe helper
     * @param other another Coordinate
     * @return distance between current and other coordinate
     */
    public double getCartesianDistance(Coordinate other) {
        // "normalize" both current and other object to cartesian coordinates, and compare the values
        // need a CartesianCoordinate to be able to access x, y, z values
        // also, the function call will implicitly reinterpret the original values into Cartesian coordinates
        CartesianCoordinate cartesianThis = asCartesianCoordinate();
        CartesianCoordinate cartesianOther = other.asCartesianCoordinate();

        double xDiff = Math.abs(cartesianOther.getX() - cartesianThis.getX());
        double yDiff = Math.abs(cartesianOther.getY() - cartesianThis.getY());
        double zDiff = Math.abs(cartesianOther.getZ() - cartesianThis.getZ());

        return Math.abs(Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2) + Math.pow(zDiff, 2)));
    }

    /**
     * Calculates central angle between two spherical coordinates.
     * Will convert other coordinate automatically, if necessary.
     *
     * @methodtype helper
     * @param other another Coordinate
     * @return central angle between current and other coordinate
     */
    public double getCentralAngle(Coordinate other) {
        // convert current instance into SphericCoordinate, if necessary
        SphericCoordinate sphericThis = asSphericCoordinate();
        SphericCoordinate sphericOther = other.asSphericCoordinate();

        // calculate latitudes and longtitudes for both coordinates
        // latitude = (90 - θ)
        // longtitude = ϕ
        double lat1 = 90 - sphericThis.getTheta();
        double lat2 = 90 - sphericOther.getTheta();
        double lon1 = sphericThis.getPhi();
        double lon2 = sphericOther.getPhi();

        // https://en.wikipedia.org/wiki/Great-circle_distance#Formulas
        double delta_lon = Math.abs(lon2 - lon1);

        double delta_sigma = Math.acos(
                Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(delta_lon)
        );

        return delta_sigma;
    }
}