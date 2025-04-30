import { useEffect } from 'react';
import { toast } from 'react-hot-toast';
import { api } from '../lib/api';

const MAPQUEST_API_KEY = 'tdxJ89S3A4RjPwZgalnHhSRoKuJTT36u';

interface Props {
  onLocationSaved?: () => void;
}

export function LocationUpdater({ onLocationSaved }: Props) {
  useEffect(() => {
    const updateLocation = async () => {
      if (!navigator.geolocation) {
        toast.error('Geolocation not supported by browser');
        return;
      }

      navigator.geolocation.getCurrentPosition(
        async (position) => {
          const { latitude, longitude } = position.coords;
          console.log('[LocationUpdater] Got position:', latitude, longitude);

          try {
            const address = await getAddressFromMapQuest(latitude, longitude);
            console.log('[LocationUpdater] Resolved address:', address);

            const res = await api.put('/api/v1/user/user-profile-latitude-longitude-update', {
              latitude,
              longitude,
            });

            if (res.data?.code === 'XS0001') {
              toast.success('Location updated');
              if (typeof onLocationSaved === 'function') onLocationSaved();
            } else {
              toast.error('Failed to update location');
            }
          } catch (err) {
            console.error('[LocationUpdater] Failed to update API', err);
            toast.error('Location update failed');
          }
        },
        (error) => {
          console.warn('[LocationUpdater] Geolocation error:', error);
          toast.error('Permission denied or error getting location');
        },
        { enableHighAccuracy: true, timeout: 10000, maximumAge: 0 }
      );
    };

    updateLocation();
  }, [onLocationSaved]);

  return null;
}

async function getAddressFromMapQuest(lat: number, lng: number): Promise<string> {
  try {
    const url = `https://www.mapquestapi.com/geocoding/v1/reverse?key=${MAPQUEST_API_KEY}&location=${lat},${lng}`;
    const response = await fetch(url);
    const data = await response.json();

    const loc = data?.results?.[0]?.locations?.[0];
    if (loc) {
      return `${loc.street}, ${loc.adminArea5}, ${loc.adminArea3}`;
    }
  } catch (err) {
    console.error('[MapQuest] Reverse geocode failed:', err);
  }

  return 'Unknown location';
}
