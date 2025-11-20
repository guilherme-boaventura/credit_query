import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CreditoService {

  private apiUrl = 'http://localhost/api/credit';

  constructor(private http: HttpClient) { }

  obterPorNumeroNfse(numeroNfse: string) : Observable<any> {
    return this.http.get(`${this.apiUrl}/invoice/${numeroNfse}`);
  }

  obterPorNumeroCredito(numeroCredito: string) : Observable<any> {
    return this.http.get(`${this.apiUrl}/${numeroCredito}`);
  }
}
