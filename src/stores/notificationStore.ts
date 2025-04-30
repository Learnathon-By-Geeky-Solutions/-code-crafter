// ✅ Updated to map user's upazila and division to latitude and longitude for nearby alerts

import { create } from 'zustand';
import { api }from '../lib/api'; // ✅ make sure this is a default export in lib/api.ts
import { toast } from 'react-hot-toast';
import { useLocationStore } from './areaLocationStore';

interface Notification {
  id: number;
  title: string;
  message?: string;
  type?: string;
  read?: boolean;
  timestamp?: string;
  description?: string;
  severityLevel?: string;
  latitude?: number;
  longitude?: number;
}

interface NotificationStore {
  notifications: Notification[];
  unread: Notification[];
  nearbyAlerts: Notification[];

  fetchNotifications: () => Promise<void>;
  fetchUnreadNotifications: () => Promise<void>;
  fetchNearbyAlerts: () => Promise<void>;

  markAsRead: (id: number) => Promise<void>;
  markAllAsRead: () => Promise<void>;

  getUnreadCount: () => number;
}

// ✅ Zustand store must be named like a hook
export const useNotificationStore = create<NotificationStore>((set, get) => ({
  notifications: [],
  unread: [],
  nearbyAlerts: [],

  fetchNotifications: async () => {
    try {
      const res = await api.get('/api/v1/alert/notifications');
      set({ notifications: res.data?.data || [] });
    } catch (err) {
      toast.error('Failed to fetch notifications');
    }
  },

  fetchUnreadNotifications: async () => {
    try {
      const res = await api.get('/api/v1/alert/notifications/unread');
      set({ unread: res.data?.data || [] });
    } catch (err) {
      toast.error('Failed to fetch unread notifications');
    }
  },

  fetchNearbyAlerts: async () => {
    try {
      const profileRes = await api.get('/api/v1/user');
      const user = profileRes.data?.data;

      const upazilaId = user?.upazila?.id;
      const district = user?.upazila?.district;
      const divisionId = district?.division?.id;

      const locationStore = useLocationStore.getState();
      const upazila = locationStore.upazilas.find(u => u.id === upazilaId);
      const division = locationStore.divisions.find(d => d.id === divisionId);

      if (!upazila || !division) {
        console.warn('[Nearby Alert] Missing location mapping from upazila/division');
        return;
      }

      const res = await api.get('/api/v1/alert/nearby', {
        params: {
          latitude: upazila.id,     // using upazila.id as latitude
          longitude: division.id,   // using division.id as longitude
        },
      });

      set({ nearbyAlerts: res.data?.data || [] });
    } catch (err: any) {
      console.error('[Nearby Alert Error]', err?.response?.data || err.message);
    }
  },

  markAsRead: async (id) => {
    try {
      await api.put(`/api/v1/alert/notifications/${id}/read`);
      await get().fetchNotifications();
    } catch {
      toast.error('Failed to mark as read');
    }
  },

  markAllAsRead: async () => {
    try {
      await api.put('/api/v1/alert/notifications/read-all');
      await get().fetchNotifications();
    } catch {
      toast.error('Failed to mark all as read');
    }
  },

  getUnreadCount: () => {
    const state = get();
    if (!Array.isArray(state.notifications)) return 0;
    return state.notifications.filter((n) => !n.read).length;
  },
}));

// ✅ Optional helper (still retained)
async function getCurrentCoords(): Promise<{ latitude: number; longitude: number }> {
  return new Promise((resolve, reject) => {
    if (!navigator.geolocation) {
      toast.error('Geolocation not supported');
      return reject();
    }

    navigator.geolocation.getCurrentPosition(
      (position) => {
        resolve({
          latitude: position.coords.latitude,
          longitude: position.coords.longitude,
        });
      },
      (err) => {
        toast.error('Location access denied');
        reject(err);
      }
    );
  });
}
