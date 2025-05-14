import React, { useEffect, useState } from 'react'
import { useRouter } from 'next/router'
import {
  getAllergens,
  updateAllergen,
  deleteAllergen,
} from '../../services/api'
import { Allergen } from '@/src/types/perfume'
import { CheckCircleIcon, TrashIcon } from '@heroicons/react/24/outline'
import ConfirmModal from '@/src/components/ConfirmModal'
import { AxiosError } from 'axios'

const AllergenDetails: React.FC = () => {
  const router = useRouter()
  const { id } = router.query
  const [allergen, setAllergen] = useState<Allergen | null>(null)
  const [loading, setLoading] = useState(true)
  const [name, setName] = useState('')
  const [maxConcentration, setMaxConcentration] = useState<number | ''>('')
  const [saving, setSaving] = useState(false)
  const [showModal, setShowModal] = useState(false)
  const [deleteError, setDeleteError] = useState("");

  useEffect(() => {
    if (typeof id === 'string') {
      fetchAllergenDetails(Number(id))
    }
  }, [id])

  const fetchAllergenDetails = async (allergenId: number) => {
    try {
      setLoading(true)
      const response = await getAllergens()
      const data =
        response.data.find((a: Allergen) => a.id === allergenId) || null
      setAllergen(data)
      if (data) {
        setName(data.name)
        setMaxConcentration(data.maxConcentration)
      }
    } finally {
      setLoading(false)
    }
  }

  const saveChanges = async () => {
    if (
      !allergen ||
      name.trim() === '' ||
      maxConcentration === '' ||
      isNaN(Number(maxConcentration))
    )
      return
    try {
      setSaving(true)
      await updateAllergen(allergen.id, {
        name: name.trim(),
        maxConcentration: Number(maxConcentration),
      })
      router.push('/allergens')
    } finally {
      setSaving(false)
    }
  }

  const handleDelete = async () => {
    if (!allergen) return;
    try {
        await deleteAllergen(allergen.id);
        router.push("/allergens");
    } catch (err: unknown) {
        console.error(err);
        // Fallback message if parsing fails
        let message = "Failed to delete allergen.";

        // Optional: check if it's a foreign key constraint
        const axiosError = err as AxiosError<{error?: string; message?: string}>;
        if (axiosError.response?.data?.message?.includes("foreign key constraint")) {
            message = "This allergen is used in one or more ingredients and cannot be deleted.";
        }

        setDeleteError(message);
    }
};

  return (
    <div className="max-w-4xl mx-auto">
      {loading ? (
        <p>Loading...</p>
      ) : allergen ? (
        <>
          <table className="table-auto border-collapse border border-gray-300 w-full">
            <thead>
              <tr>
                <th className="border border-gray-300 px-4 py-2">#</th>
                <th className="border border-gray-300 px-4 py-2">Name</th>
                <th className="border border-gray-300 px-4 py-2">
                  Max Concentration (%)
                </th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td className="border border-gray-300 px-4 py-2 text-center">
                  {allergen.id}
                </td>
                <td className="border border-gray-300 px-4 py-2">
                  <input
                    type="text"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    className="w-full bg-transparent focus:outline-none focus:ring-2 focus:ring-blue-300 px-2 py-1"
                  />
                </td>
                <td className="border border-gray-300 px-4 py-2">
                  <input
                    type="number"
                    value={maxConcentration}
                    onChange={(e) =>
                      setMaxConcentration(
                        e.target.value === '' ? '' : parseFloat(e.target.value)
                      )
                    }
                    className="w-full bg-transparent focus:outline-none focus:ring-2 focus:ring-blue-300 px-2 py-1"
                  />
                </td>
              </tr>
            </tbody>
          </table>
          <div className="w-full flex justify-end gap-4 p-4 border-t mt-6">
            <button title="Save" onClick={saveChanges} disabled={saving}>
              <CheckCircleIcon
                className={`h-6 w-6 ${
                  saving ? 'text-gray-400' : 'text-green-700'
                }`}
              />
            </button>
            <button title="Delete" onClick={() => setShowModal(true)}>
              <TrashIcon className="h-6 w-6 text-red-400" />
            </button>
          </div>
          <ConfirmModal
            isOpen={showModal}
            title="Confirm Deletion"
            message="Are you sure you want to delete this allergen? This action cannot be undone."
            error={deleteError}
            onConfirm={handleDelete}
            onCancel={() => setShowModal(false)}
          />
        </>
      ) : (
        <p>Allergen not found.</p>
      )}
    </div>
  )
}

export default AllergenDetails
