/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hedspi.tienlv.grapp.model.placeapi;

import java.util.List;

/**
 *
 * @author Lev Tien
 */
public class NearBySearchResult {

	private List<String> htmlAttribution;
	private String nextPageToken;
	private List<Result> results;
	private String status;

	public NearBySearchResult() {
	}

	public NearBySearchResult(List<String> htmlAttribution, String nextPageToken, List<Result> results, String status) {
		this.htmlAttribution = htmlAttribution;
		this.nextPageToken = nextPageToken;
		this.results = results;
		this.status = status;
	}

	public List<String> getHtmlAttribution() {
		return htmlAttribution;
	}

	public void setHtmlAttribution(List<String> htmlAttribution) {
		this.htmlAttribution = htmlAttribution;
	}

	public String getNextPageToken() {
		return nextPageToken;
	}

	public void setNextPageToken(String nextPageToken) {
		this.nextPageToken = nextPageToken;
	}

	public List<Result> getResults() {
		return results;
	}

	public void setResults(List<Result> results) {
		this.results = results;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
