/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hedspi.tienlv.grapp.model.placeapi;

/**
 *
 * @author Lev Tien
 */
public class Geometry {
	private Location location;

	public Geometry() {
	}

	public Geometry(Location location) {
		this.location = location;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

}
