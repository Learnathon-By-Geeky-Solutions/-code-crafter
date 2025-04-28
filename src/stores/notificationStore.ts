import { create } from 'zustand';

export interface Notification {
  id: string;
  title: string;
  message: string;
  type: 'appointment' | 'medicine' | 'system' | 'blood';
  timestamp: string;
  read: boolean;
}

interface NotificationStore {
  notifications: Notification[];
  markAsRead: (notificationId: string) => void;
  markAllAsRead: () => void;
  getUnreadCount: () => number;
}

export const useNotificationStore = create<NotificationStore>((set, get) => ({
  notifications: [
    {
      id: '1',
      title: 'Appointment Reminder',
      message: 'Your appointment with Dr. Sarah Johnson is tomorrow at 10:00 AM',
      type: 'appointment',
      timestamp: '2024-03-19T10:00:00Z',
      read: false
    },
    {
      id: '2',
      title: 'Medicine Reminder',
      message: 'Time to take your evening medication',
      type: 'medicine',
      timestamp: '2024-03-19T09:30:00Z',
      read: false
    }
  ],

  markAsRead: (notificationId: string) => {
    set(state => ({
      notifications: state.notifications.map(notification =>
        notification.id === notificationId
          ? { ...notification, read: true }
          : notification
      )
    }));
  },

  markAllAsRead: () => {
    set(state => ({
      notifications: state.notifications.map(notification => ({
        ...notification,
        read: true
      }))
    }));
  },

  getUnreadCount: () => {
    return get().notifications.filter(n => !n.read).length;
  }
}));