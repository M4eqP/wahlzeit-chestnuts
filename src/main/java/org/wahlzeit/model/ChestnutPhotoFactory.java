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

import org.wahlzeit.utils.PatternInstance;

@PatternInstance(
	name = "FactoryMethod",
	participants = {
		"Product"
	}
)
@PatternInstance(
    name = "DependencyInjection",
    participants = {
        "Service"
    }
)
public class ChestnutPhotoFactory extends PhotoFactory {
    /**
     * @methodtype constructor
     */
    protected ChestnutPhotoFactory() {
        super();
    }

    /**
     * @methodtype factory
     */
    public Photo createPhoto() {
        return new ChestnutPhoto();
    }

    /**
     * Creates a new photo with the specified id
     * @methodtype factory
     */
    public Photo createPhoto(PhotoId id) {
        // preconditions
        assertNotNull(id);

        return new ChestnutPhoto(id);
    }
}
