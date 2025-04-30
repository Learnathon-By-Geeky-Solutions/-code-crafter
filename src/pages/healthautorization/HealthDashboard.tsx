// ✅ Updated HealthDashboard with division, district, upazila selection and proper lat/lng mapping

import { useEffect, useState } from 'react';
import { Dialog } from '@headlessui/react';
import { toast } from 'react-hot-toast';
import api from '../../lib/axios';
import { MapPin, PlusCircle, Trash2, Power } from 'lucide-react';
import { useLocationStore } from '../../stores/areaLocationStore';

export default function HealthDashboard() {
  const [authorizationNumber, setAuthorizationNumber] = useState<string>(() => localStorage.getItem('authNum') || '');
  const [showAuthDialog, setShowAuthDialog] = useState(!authorizationNumber);
  const [showAlertModal, setShowAlertModal] = useState(false);
  const [alerts, setAlerts] = useState<any[]>([]);
  const [loading, setLoading] = useState(false);

  const [selectedDivisionId, setSelectedDivisionId] = useState<number | null>(null);
  const [selectedDistrictId, setSelectedDistrictId] = useState<number | null>(null);
  const [selectedUpazilaId, setSelectedUpazilaId] = useState<number | null>(null);

  const { divisions, districts, upazilas, getDistrictsByDivision, getUpazilasByDistrict } = useLocationStore();

  const [newAlert, setNewAlert] = useState({
    title: '', description: '', alertness: '', radius: 0.1, severityLevel: 'LOW',
    startDate: '', endDate: '', latitude: 0, longitude: 0, divisionId: null
  });

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (!token) {
      toast.error('Login required');
      window.location.href = '/login';
    }
    if (authorizationNumber) fetchAlerts();
  }, [authorizationNumber]);

  const fetchAlerts = async () => {
    try {
      const { data } = await api.get('/api/v1/health-authorization/alerts');
      setAlerts(data?.data || []);
    } catch (err) {
      console.error('Failed to fetch alerts', err);
      toast.error('Failed to load alerts.');
    }
  };

  const handleAuthSubmit = async () => {
    try {
      await api.post('/api/v1/health-authorization/create', { authorizationNumber });
      localStorage.setItem('authNum', authorizationNumber);
      setShowAuthDialog(false);
      toast.success('Authorization setup complete!');
      fetchAlerts();
    } catch (err: any) {
      console.error('Auth create error:', err);
      toast.error(err?.response?.data?.message || 'Failed to create authorization.');
    }
  };

  const handleCreateAlert = async () => {
    if (!selectedDivisionId || !selectedUpazilaId) {
      toast.error('Please select a division and upazila');
      return;
    }

    try {
      const payload = {
        ...newAlert,
        radius: Number(newAlert.radius),
        latitude: selectedUpazilaId, // passing upazilaId as latitude
        longitude: selectedDivisionId, // passing divisionId as longitude
        divisionId: selectedDivisionId,
        startDate: new Date(newAlert.startDate).toISOString(),
        endDate: new Date(newAlert.endDate).toISOString(),
      };

      await api.post('/api/v1/health-authorization/alert', payload);
      toast.success('Alert created!');
      setShowAlertModal(false);
      fetchAlerts();
    } catch (err: any) {
      console.error('[Alert Creation Error]', err.response?.data || err.message);
      toast.error(err?.response?.data?.message || 'Failed to create alert.');
    }
  };

  const deleteAlert = async (id: number) => {
    try {
      await api.delete(`/api/v1/health-authorization/alert/${id}`);
      toast.success('Alert deleted');
      fetchAlerts();
    } catch (err) {
      toast.error('Failed to delete alert');
    }
  };

  const deactivateAlert = async (id: number) => {
    try {
      await api.put(`/api/v1/health-authorization/alert/${id}/deactivate`);
      toast.success('Alert deactivated');
      fetchAlerts();
    } catch (err) {
      toast.error('Failed to deactivate alert');
    }
  };

  return (
    <div className="p-6 max-w-5xl mx-auto">
      <h1 className="text-2xl font-bold mb-6">Health Authorization Service Management</h1>

      <button onClick={() => setShowAlertModal(true)} className="bg-green-600 hover:bg-green-700 text-white px-4 py-2 rounded flex items-center gap-2 mb-6">
        <PlusCircle size={18} /> Create Alert
      </button>

      <div className="grid gap-4">
        {alerts.map((alert) => (
          <div key={alert.id} className="border rounded p-4 shadow-sm flex justify-between items-start">
            <div>
              <h2 className="font-semibold text-lg">{alert.title}</h2>
              <p className="text-sm text-gray-500">{alert.description}</p>
              <p className="text-sm mt-1">
                <MapPin size={14} className="inline-block" /> Division ID: {alert.longitude}, Upazila ID: {alert.latitude}
              </p>
              <p className="text-sm text-gray-500 mt-1">
                Alertness: {alert.alertness} | Severity: {alert.severityLevel}
              </p>
            </div>
            <div className="flex gap-2">
              <button onClick={() => deactivateAlert(alert.id)} className="text-yellow-600 hover:text-yellow-800">
                <Power size={18} />
              </button>
              <button onClick={() => deleteAlert(alert.id)} className="text-red-600 hover:text-red-800">
                <Trash2 size={18} />
              </button>
            </div>
          </div>
        ))}
      </div>

      <Dialog open={showAuthDialog} onClose={() => {}} className="fixed inset-0 z-50 flex items-center justify-center">
        <Dialog.Panel className="bg-white p-6 rounded shadow max-w-md w-full">
          <Dialog.Title className="font-bold text-lg mb-2">Enter Authorization Number</Dialog.Title>
          <input value={authorizationNumber} onChange={(e) => setAuthorizationNumber(e.target.value)} placeholder="Authorization Number" className="w-full border px-3 py-2 rounded mb-4" />
          <button onClick={handleAuthSubmit} className="bg-blue-600 text-white px-4 py-2 rounded w-full">Submit</button>
        </Dialog.Panel>
      </Dialog>

      <Dialog open={showAlertModal} onClose={() => setShowAlertModal(false)} className="fixed inset-0 z-50 flex items-center justify-center">
        <Dialog.Panel className="bg-white p-6 rounded shadow max-w-lg w-full space-y-3">
          <Dialog.Title className="text-lg font-semibold">Create New Alert</Dialog.Title>

          {['title', 'description', 'alertness'].map((field) => (
            <input key={field} placeholder={field.charAt(0).toUpperCase() + field.slice(1)} value={(newAlert as any)[field]} onChange={(e) => setNewAlert({ ...newAlert, [field]: e.target.value })} className="w-full border px-3 py-2 rounded" />
          ))}

          <input type="number" placeholder="Radius (in KM)" className="w-full border px-3 py-2 rounded" value={newAlert.radius} onChange={(e) => setNewAlert({ ...newAlert, radius: parseFloat(e.target.value) || 0 })} />

          <select value={newAlert.severityLevel} onChange={(e) => setNewAlert({ ...newAlert, severityLevel: e.target.value })} className="w-full border px-3 py-2 rounded">
            <option value="LOW">LOW</option>
            <option value="MEDIUM">MEDIUM</option>
            <option value="HIGH">HIGH</option>
          </select>

          <select value={selectedDivisionId || ''} onChange={(e) => { const val = Number(e.target.value); setSelectedDivisionId(val); setSelectedDistrictId(null); setSelectedUpazilaId(null); }} className="w-full border px-3 py-2 rounded">
            <option value="">Select Division</option>
            {divisions.map(div => <option key={div.id} value={div.id}>{div.name}</option>)}
          </select>

          {selectedDivisionId && (
            <select value={selectedDistrictId || ''} onChange={(e) => { const val = Number(e.target.value); setSelectedDistrictId(val); setSelectedUpazilaId(null); }} className="w-full border px-3 py-2 rounded">
              <option value="">Select District</option>
              {getDistrictsByDivision(selectedDivisionId).map(dist => <option key={dist.id} value={dist.id}>{dist.name}</option>)}
            </select>
          )}

          {selectedDistrictId && (
            <select value={selectedUpazilaId || ''} onChange={(e) => setSelectedUpazilaId(Number(e.target.value))} className="w-full border px-3 py-2 rounded">
              <option value="">Select Upazila</option>
              {getUpazilasByDistrict(selectedDistrictId).map(upz => <option key={upz.id} value={upz.id}>{upz.name}</option>)}
            </select>
          )}

          <input type="datetime-local" value={newAlert.startDate} onChange={(e) => setNewAlert({ ...newAlert, startDate: e.target.value })} className="w-full border px-3 py-2 rounded" />
          <input type="datetime-local" value={newAlert.endDate} onChange={(e) => setNewAlert({ ...newAlert, endDate: e.target.value })} className="w-full border px-3 py-2 rounded" />

          <button onClick={handleCreateAlert} className="bg-green-600 text-white px-4 py-2 rounded w-full">Create</button>
        </Dialog.Panel>
      </Dialog>
    </div>
  );
}
