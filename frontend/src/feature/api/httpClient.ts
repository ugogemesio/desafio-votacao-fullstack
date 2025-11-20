import axios, { AxiosError  } from "axios";
import type { AxiosResponse, InternalAxiosRequestConfig } from "axios";

export const http = axios.create({
  baseURL: "https://desafio-votacao-fullstack-7qd4.onrender.com/api/v1/",
  //baseURL: "http://localhost:8080/api/v1/",
  timeout: 10000,
  headers: {
    "Content-Type": "application/json",
  },
});


http.interceptors.request.use((config: InternalAxiosRequestConfig) => {
  return config;
});


http.interceptors.response.use(
  (response: AxiosResponse) => response,
  (error: AxiosError) => {
    console.error("API error:", error);
    return Promise.reject(error);
  }
);