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
public class Photo {
	private long height;
	private long width;
	private String photo_reference;

	public Photo() {
	}

	public Photo(long height, long width, String photo_reference) {
		this.height = height;
		this.width = width;
		this.photo_reference = photo_reference;
	}

	public long getHeight() {
		return height;
	}

	public void setHeight(long height) {
		this.height = height;
	}

	public long getWidth() {
		return width;
	}

	public void setWidth(long width) {
		this.width = width;
	}

	public String getPhoto_reference() {
		return photo_reference;
	}

	public void setPhoto_reference(String photo_reference) {
		this.photo_reference = photo_reference;
	}

}
