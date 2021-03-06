import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { clone } from 'ramda';

import { Api, HttpOptions, HttpOptionsAuthorized } from '../modules/api';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class TripsService {
  constructor(private http: HttpClient) { }

  setExistingTrips(): Observable<any> {
    return this.http.get<any>(Api.trips.getExistingPlanets(), HttpOptionsAuthorized);
  }

  createTrip(payload): Observable<any> {
    return this.http.post<any>(Api.trips.addTrip(), payload, HttpOptionsAuthorized);
  }

  updateTrip(payload, id): Observable<any> {
    return this.http.put<any>(Api.trips.updateTrip(id), payload, HttpOptionsAuthorized);
  }

  getAllTrips(limit = 10, offset = 0): Observable<any> {
    return this.http.get<any>(Api.trips.getAllTrips(limit, offset), HttpOptionsAuthorized)
  }

  getTripsForCarrier(carrierId: number): Observable<any> {
    return this.http.get<any>(Api.trips.getCarrierTrips() + '?carrier_id=' + carrierId, HttpOptionsAuthorized)
  }
  
  saveTicketClass(payload) {
    return this.http.post<any>(Api.trips.addTicketClass(),payload, HttpOptionsAuthorized);
  }

  deleteTicketClass(id) {
    return this.http.delete<any>(Api.trips.deleteTicketClass(id), HttpOptionsAuthorized);
  }

  updateTripStatus(payload, id) {
    return this.http.patch<any>(Api.trips.deleteTrip(id), payload, HttpOptionsAuthorized);
  }
}
