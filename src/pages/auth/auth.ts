// src/pages/auth/auth.ts

import { setAuthToken } from '../../lib/api'; // ✅ correct path now

export const logout = () => {
  localStorage.removeItem('xenon_access_token'); // ✅ Clear token
  setAuthToken(''); // ✅ Clear Axios auth header
};
