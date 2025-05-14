import React, { useState } from 'react'
import AllergenList from '@/src/components/AllergenList'
import { PlusCircleIcon } from '@heroicons/react/16/solid'
import AddAllergenModal from '@/src/components/AddAllergenModal'
import { createAllergen } from '@/src/services/api'

const AllergensPage: React.FC = () => {
  const [showModal, setShowModal] = useState(false);
  const [listKey, setListKey] = useState(0)

  const handleAddAllergen = async (name: string) => {
    await createAllergen({name});
    setListKey(prev => prev + 1)
  }
  return (
    <div className="max-w-4xl mx-auto">
      <h1 className="text-2xl font-bold">Allergens</h1>
      <AllergenList key={listKey} />
      <div className="pr-4 flex justify-end mt-4">
        <button onClick={() => setShowModal(true)} aria-label="Add Allergen">
          <PlusCircleIcon className="h-6 w-6 text-primary hover:text-primary/70 cursor-pointer" />
        </button>
      </div>

      <AddAllergenModal
        isOpen={showModal}
        onClose={() => setShowModal(false)}
        onAdd={(name) => {return handleAddAllergen(name)}}
      />
    </div>
  )
}

export default AllergensPage
