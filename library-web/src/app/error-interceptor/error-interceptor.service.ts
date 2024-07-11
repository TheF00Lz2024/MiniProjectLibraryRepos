import { HttpHandlerFn, HttpInterceptorFn } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, throwError } from 'rxjs';

export const ErrorInterceptorService: HttpInterceptorFn = (request, next: HttpHandlerFn) => {
  return next(request).pipe(catchError((error) => {
    console.log(`This error is from error interceptor, ${error.error.message}`);
    alert(error.error.message);
    return throwError(() => error);
  }))
}
