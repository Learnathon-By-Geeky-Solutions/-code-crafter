import { create } from 'zustand';

export interface Division {
  id: number;
  name: string;
}

export interface District {
  id: number;
  name: string;
  division_id: number;
}

export interface Upazila {
  id: number;
  name: string;
  district_id: number;
}

interface LocationStore {
  divisions: Division[];
  districts: District[];
  upazilas: Upazila[];

  getDistrictsByDivision: (divisionId: number) => District[];
  getUpazilasByDistrict: (districtId: number) => Upazila[];
}

const divisions: Division[] = [
  { id: 1, name: 'Barisal' },
  { id: 2, name: 'Chattogram' },
  { id: 3, name: 'Dhaka' },
  { id: 4, name: 'Khulna' },
  { id: 5, name: 'Rajshahi' },
  { id: 6, name: 'Rangpur' },
  { id: 7, name: 'Sylhet' },
  { id: 8, name: 'Mymensingh' },
];

const districts: District[] = [
  { id: 1, name: 'Dhaka', division_id: 3 },
  { id: 2, name: 'Faridpur', division_id: 3 },
  { id: 3, name: 'Gazipur', division_id: 3 },
  { id: 4, name: 'Gopalganj', division_id: 3 },
  { id: 5, name: 'Jamalpur', division_id: 8 },
  { id: 6, name: 'Kishoreganj', division_id: 3 },
  { id: 7, name: 'Madaripur', division_id: 3 },
  { id: 8, name: 'Manikganj', division_id: 3 },
  { id: 9, name: 'Munshiganj', division_id: 3 },
  { id: 10, name: 'Mymensingh', division_id: 8 },
];

const upazilas: Upazila[] = [
  { id: 1, name: 'Dhamrai', district_id: 1 },
  { id: 2, name: 'Dohar', district_id: 1 },
  { id: 3, name: 'Keraniganj', district_id: 1 },
  { id: 4, name: 'Nawabganj', district_id: 1 },
  { id: 5, name: 'Savar', district_id: 1 },
  { id: 6, name: 'Faridpur Sadar', district_id: 2 },
  { id: 7, name: 'Boalmari', district_id: 2 },
  { id: 8, name: 'Gazipur Sadar-Joydebpur', district_id: 3 },
  { id: 9, name: 'Kaliakior', district_id: 3 },
  { id: 10, name: 'Gopalganj Sadar', district_id: 4 }
];

export const useLocationStore = create<LocationStore>((set, get) => ({
  divisions,
  districts,
  upazilas,

  getDistrictsByDivision: (divisionId: number) =>
    get().districts.filter((d) => d.division_id === divisionId),

  getUpazilasByDistrict: (districtId: number) =>
    get().upazilas.filter((u) => u.district_id === districtId),
}));
