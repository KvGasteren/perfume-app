import React, { useEffect, useState } from 'react';
import { useRouter } from 'next/router';
import {
  getIngredientById,
  getAllergens,
  updateIngredient,
  deleteIngredient,
} from '@/src/services/api';
import { Ingredient, Allergen, IngredientAllergen } from '@/src/types/perfume';
import { CheckCircleIcon, TrashIcon, PlusCircleIcon } from '@heroicons/react/24/outline';
import ConfirmModal from '@/src/components/ConfirmModal';
import { AxiosError } from 'axios';

const IngredientDetails: React.FC = () => {
  const router = useRouter();
  const { id } = router.query;
  const [ingredient, setIngredient] = useState<Ingredient | null>(null);
  const [allAllergens, setAllAllergens] = useState<Allergen[]>([]);
  const [name, setName] = useState('');
  const [allergens, setAllergens] = useState<IngredientAllergen[]>([]);
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [showModal, setShowModal] = useState(false);
  const [deleteError, setDeleteError] = useState('');

  useEffect(() => {
    if (typeof id === 'string') {
      fetchData(Number(id));
    }
  }, [id]);

  const fetchData = async (ingredientId: number) => {
    try {
      setLoading(true);
      const [ingredientRes, allergensRes] = await Promise.all([
        getIngredientById(ingredientId),
        getAllergens(),
      ]);
      const ing = ingredientRes.data;
      setIngredient(ing);
      setName(ing.name);
      setAllergens(ing.allergens);
      setAllAllergens(allergensRes.data);
    } finally {
      setLoading(false);
    }
  };

  const updateAllergen = (index: number, field: 'concentration' | 'allergenId', value: number) => {
    const updated = [...allergens];
    if (field === 'allergenId') {
      updated[index] = allAllergens.find(a => a.id === value)!;
    } else {
      updated[index].concentration = value;
    }
    setAllergens(updated);
  };

  const addAllergen = () => {
    const unused = allAllergens.find(
      a => !allergens.some(ia => ia.id === a.id)
    );
    if (unused) {
      setAllergens([...allergens, { ...unused, concentration: 0 }]);
    }
  };

  const removeAllergen = (index: number) => {
    setAllergens(allergens.filter((_, i) => i !== index));
  };

  const saveChanges = async () => {
    if (!ingredient || name.trim() === '') return;
    try {
      setSaving(true);
      await updateIngredient(ingredient.id, {
        ...ingredient,
        name: name.trim(),
        allergens,
      });
      router.push('/ingredients');
    } finally {
      setSaving(false);
    }
  };

  const handleDelete = async () => {
    if (!ingredient) return;
    try {
      await deleteIngredient(ingredient.id);
      router.push('/ingredients');
    } catch (err) {
      console.error(err);
      let message = 'Failed to delete ingredient.';
      const axiosError = err as AxiosError<{ message?: string }>;
      if (axiosError.response?.data?.message?.includes('foreign key constraint')) {
        message = 'This ingredient is used in a formula and cannot be deleted.';
      }
      setDeleteError(message);
    }
  };

  return (
    <div className="p-4">
      {loading ? (
        <p>Loading...</p>
      ) : ingredient ? (
        <>
          <div className="mb-4">
            <label className="block text-sm font-medium">Name</label>
            <input
              type="text"
              value={name}
              onChange={(e) => setName(e.target.value)}
              className="w-full border border-gray-300 rounded px-2 py-1"
            />
          </div>

          <div>
            <label className="block text-sm font-medium mb-2">Allergens</label>
            <div className="grid grid-cols-3 gap-2 font-medium text-sm mb-2">
              <div>Name</div>
              <div>Concentration (%)</div>
              <div></div>
            </div>
            {allergens.map((ia, index) => (
              <div key={index} className="flex gap-2 items-center mb-2">
                <select
                  value={ia.id}
                  onChange={(e) => updateAllergen(index, 'allergenId', Number(e.target.value))}
                  className="border border-gray-300 rounded px-2 py-1 w-1/3"
                >
                  {allAllergens.map((a) => (
                    <option key={a.id} value={a.id}>{a.name}</option>
                  ))}
                </select>
                <input
                  type="number"
                  value={ia.concentration}
                  onChange={(e) => updateAllergen(index, 'concentration', parseFloat(e.target.value))}
                  className="border border-gray-300 rounded px-2 py-1 w-1/3"
                  placeholder="Concentration %"
                />
                <button onClick={() => removeAllergen(index)} className="text-red-500 text-sm">Remove</button>
              </div>
            ))}
            <button onClick={addAllergen} className="flex items-center gap-1 text-sm text-blue-600 mt-2">
              <PlusCircleIcon className="h-4 w-4" /> Add Allergen
            </button>
          </div>

          <div className="flex justify-end gap-4 mt-6">
            <button onClick={saveChanges} disabled={saving} title="Save">
              <CheckCircleIcon className={`h-6 w-6 ${saving ? 'text-gray-400' : 'text-green-700'}`} />
            </button>
            <button onClick={() => setShowModal(true)} title="Delete">
              <TrashIcon className="h-6 w-6 text-red-400" />
            </button>
          </div>

          <ConfirmModal
            isOpen={showModal}
            title="Confirm Deletion"
            message="Are you sure you want to delete this ingredient? This action cannot be undone."
            error={deleteError}
            onConfirm={handleDelete}
            onCancel={() => setShowModal(false)}
          />
        </>
      ) : (
        <p>Ingredient not found.</p>
      )}
    </div>
  );
};

export default IngredientDetails;