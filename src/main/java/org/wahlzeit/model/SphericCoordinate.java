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
 * Represents cartesian coordinate.
 */
public class SphericCoordinate implements Coordinate {
    // cartesian coordinates
    private final double phi;
    private final double theta;
    private final double radius;

    /**
     * @methodtype constructor
     * @param phi phi value
     * @param theta theta value
     * @param radius radius value
     */
    public SphericCoordinate(double phi, double theta, double radius) {
        // validate the values are in correct ranges
        if (radius < 0)
            throw new IllegalArgumentException("radius must be positive value: " + radius);

        if (phi < -Math.PI || phi >= Math.PI)
            throw new IllegalArgumentException("phi must be in range [-π, π[ (a.k.a. [-180°, 180°[): " + phi);

        if (theta < 0 || theta > Math.PI)
            throw new IllegalArgumentException("theta must be in range [0, π] (a.k.a. [0°, 180°]: " + theta);

        this.phi = phi;
        this.theta = theta;
        this.radius = radius;
    };

    /**
     * @methodtype get
     * Returns coordinate's phi value
     * @return X value
     */
    public double getPhi() {
        return phi;
    }

    /**
     * @methodtype get
     * Returns coordinate's theta value
     * @return Y value
     */
    public double getTheta() {
        return theta;
    }

    /**
     * @methodtype get
     * Returns coordinate's radius value
     * @return Z value
     */
    public double getRadius() {
        return radius;
    }

    /**
     * @methodtype boolean-query
     * Checks whether two coordinates are equal.
     * @param other another CartesianCoordinate
     * @return if other CartesianCoordinate is equal with this one
     */
    public boolean isEqual(Coordinate other) {
        // need a SphericCoordinate to be able to access phi, theta and radius values
        // also, the function call will implicitly reinterpret the original values into spheric coordinates
        SphericCoordinate sphericOther = other.asSphericCoordinate();

        return this.phi == sphericOther.phi && this.theta == sphericOther.theta && this.radius == sphericOther.radius;
    }

    /**
     * @methodtype compare
     * Checks whether two coordinates are equal.
     * @param other an ther CartesianCoordinate
     * @return true if both are equal, false otherwise
     */
    public boolean equals(Coordinate other) {
        return isEqual(other);
    }

    /**
     * Calculates direct cartesian distance between two cartesian coordinates.
     * Will convert current coordinate into Cartesian one and then perform calculation as implemented in that class.
     *
     * @methodytpe helper
     * @param other another Coordinate
     * @return distance between current and other coordinate
     */
    public double getCartesianDistance(Coordinate other) {
        // convert current instance into CartesianCoordinate, then we can simply call the function there
        return asCartesianCoordinate().getCartesianDistance(other);
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
        SphericCoordinate sphericOther = other.asSphericCoordinate();

        // calculate latitudes and longtitudes for both coordinates
        // latitude = (90 - θ)
        // longtitude = ϕ
        double lat1 = 90 - getTheta();
        double lat2 = 90 - sphericOther.getTheta();
        double lon1 = getPhi();
        double lon2 = sphericOther.getPhi();

        // https://en.wikipedia.org/wiki/Great-circle_distance#Formulas
        double delta_lon = Math.abs(lon2 - lon1);

        double delta_sigma = Math.acos(
            Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(delta_lon)
        );

        return delta_sigma;
    }

    /**
     * "Convert" current instance into CartesianCoordinate
     *
     * @methodtype conversion
     */
    public CartesianCoordinate asCartesianCoordinate() {
        // calculate x, y and z values from own values
        double x = radius * Math.sin(theta) * Math.cos(phi);
        double y = radius * Math.sin(theta) * Math.sin(phi);
        double z = radius * Math.cos(theta);

        // use these values to construct a new CartesianCoordinate
        return new CartesianCoordinate(x, y ,z);
    }

    /**
     * "Convert" current instance into SphericCoordinate
     *
     * @methodtype conversion
     */
    public SphericCoordinate asSphericCoordinate() {
        // As we are in the SphericCoordinate class body, we can just return the current instance
        return this;
    }
}