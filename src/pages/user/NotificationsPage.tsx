import { Bell, CheckCircle, Clock } from 'lucide-react';
import { useNotificationStore } from '../../stores/notificationStore';

export function NotificationsPage() {
  const { notifications, markAsRead, markAllAsRead, getUnreadCount } = useNotificationStore();
  const unreadCount = getUnreadCount();

  const getNotificationIcon = (type: string) => {
    switch (type) {
      case 'appointment':
        return <Clock className="text-blue-500" />;
      case 'medicine':
        return <Bell className="text-green-500" />;
      case 'blood':
        return <Bell className="text-red-500" />;
      default:
        return <Bell className="text-gray-500" />;
    }
  };

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="max-w-3xl mx-auto">
        <div className="flex items-center justify-between mb-8">
          <h1 className="text-2xl font-bold">Notifications</h1>
          {unreadCount > 0 && (
            <button
              onClick={markAllAsRead}
              className="text-blue-500 hover:text-blue-600"
            >
              Mark all as read
            </button>
          )}
        </div>

        <div className="space-y-4">
          {notifications.length === 0 ? (
            <div className="text-center py-12 bg-gray-50 rounded-lg">
              <Bell size={48} className="text-gray-400 mx-auto mb-4" />
              <p className="text-gray-500">No notifications</p>
            </div>
          ) : (
            notifications.map((notification) => (
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
                    <p className="text-gray-600 mb-2">{notification.message}</p>
                    <div className="flex items-center justify-between">
                      <span className="text-sm text-gray-500">
                        {new Date(notification.timestamp).toLocaleString()}
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
      </div>
    </div>
  );
}