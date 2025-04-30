// src/lib/api.ts

import axios from 'axios';

const BASE_URL = 'https://api.xenonhealthcare.xyz';

// Create Axios instance
export const api = axios.create({
  baseURL: BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Function to manually set token inside headers
export const setAuthToken = (token: string | null) => {
  if (token) {
    api.defaults.headers.common['Authorization'] = `Bearer ${token}`;
  } else {
    delete api.defaults.headers.common['Authorization'];
  }
};

// Setup token automatically if already saved in localStorage
const token = localStorage.getItem('token') || localStorage.getItem('xenon_access_token');
setAuthToken(token);
