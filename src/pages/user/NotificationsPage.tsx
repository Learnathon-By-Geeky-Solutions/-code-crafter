import { useState } from 'react';
import { Bell, CheckCircle, Clock, AlertCircle, MapPin } from 'lucide-react';
import { useNotificationStore } from '../../stores/notificationStore'; // ✅ useNotificationStore, not NotificationsPage
import { LocationUpdater } from '../../components/LocationUpdater';
import { AxiosError } from 'axios';

export function NotificationsPage() {
  const {
    notifications,
    nearbyAlerts,
    getUnreadCount,
    fetchNotifications,
    fetchNearbyAlerts,
    markAsRead,
    markAllAsRead
  } = useNotificationStore();

  const safeNotifications = Array.isArray(notifications) ? notifications : [];
  const safeNearbyAlerts = Array.isArray(nearbyAlerts) ? nearbyAlerts : [];
  const unreadCount = typeof getUnreadCount === 'function' ? getUnreadCount() : 0;

  const [locationSynced, setLocationSynced] = useState(false);

  const handleLocationSaved = async () => {
    try {
      await fetchNotifications();
      await fetchNearbyAlerts();
      setLocationSynced(true);
    } catch (err) {
      const axiosErr = err as AxiosError;
      console.error('[Fetch Error]', axiosErr?.response?.data || axiosErr.message);
    }
  };

  const getNotificationIcon = (type?: string) => {
    switch (type) {
      case 'appointment':
        return <Clock className="text-blue-500" />;
      case 'medicine':
        return <Bell className="text-green-500" />;
      case 'blood':
        return <AlertCircle className="text-red-500" />;
      default:
        return <Bell className="text-gray-500" />;
    }
  };

  return (
    <div className="container mx-auto px-4 py-8">
      {/* ⏳ Ensure location is saved before fetching alerts */}
      {!locationSynced && <LocationUpdater onLocationSaved={handleLocationSaved} />}

      <div className="max-w-3xl mx-auto">
        {/* Header */}
        <div className="flex items-center justify-between mb-8">
          <h1 className="text-2xl font-bold">Notifications</h1>
          {unreadCount > 0 && (
            <button onClick={markAllAsRead} className="text-blue-500 hover:text-blue-600">
              Mark all as read
            </button>
          )}
        </div>

        {/* Notification List */}
        <div className="space-y-4 mb-12">
          {safeNotifications.length === 0 ? (
            <div className="text-center py-12 bg-gray-50 rounded-lg">
              <Bell size={48} className="text-gray-400 mx-auto mb-4" />
              <p className="text-gray-500">No notifications</p>
            </div>
          ) : (
            safeNotifications.map((notification) => (
              <div
                key={notification.id}
                className={`bg-white rounded-lg shadow-sm p-4 ${
                  !notification.read ? 'border-l-4 border-blue-500' : ''
                }`}
              >
                <div className="flex items-start gap-4">
                  <div className="p-2 bg-gray-50 rounded-lg">
                    {getNotificationIcon(notification.type)}
                  </div>
                  <div className="flex-grow">
                    <h3 className="font-semibold mb-1">{notification.title}</h3>
                    {notification.message && (
                      <p className="text-gray-600 mb-2">{notification.message}</p>
                    )}
                    <div className="flex items-center justify-between">
                      <span className="text-sm text-gray-500">
                        {notification.timestamp
                          ? new Date(notification.timestamp).toLocaleString()
                          : 'No timestamp'}
                      </span>
                      {!notification.read && (
                        <button
                          onClick={() => markAsRead(notification.id)}
                          className="flex items-center gap-1 text-blue-500 hover:text-blue-600"
                        >
                          <CheckCircle size={16} />
                          <span>Mark as read</span>
                        </button>
                      )}
                    </div>
                  </div>
                </div>
              </div>
            ))
          )}
        </div>

        {/* Nearby Alerts */}
        <div className="mt-10">
          <h2 className="text-xl font-semibold mb-4 flex items-center gap-2">
            <MapPin size={20} className="text-red-500" />
            Nearby Alerts
          </h2>

          {safeNearbyAlerts.length === 0 ? (
            <p className="text-gray-500">No nearby alerts found.</p>
          ) : (
            <div className="space-y-4">
              {safeNearbyAlerts.map((alert) => (
                <div
                  key={alert.id}
                  className="bg-white rounded-lg shadow-sm p-4 border-l-4 border-red-500"
                >
                  <h3 className="font-semibold text-lg">{alert.title}</h3>
                  {alert.description && (
                    <p className="text-gray-600">{alert.description}</p>
                  )}
                  <p className="text-sm text-gray-500 mt-1">
                    Severity: {alert.severityLevel || 'N/A'}
                  </p>
                  <p className="text-sm text-gray-500">
                    Location: {alert.latitude ?? '--'}, {alert.longitude ?? '--'}
                  </p>
                </div>
              ))}
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
