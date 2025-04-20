import React from 'react'
import AllergenList from '@/src/components/AllergenList'
import { PlusCircleIcon } from '@heroicons/react/16/solid'

const AllergensPage: React.FC = () => {
  return (
    <div className="p-4">
      <h1 className="text-2xl font-bold">Allergens</h1>
      <AllergenList />
      <div className="pr-4 flex justify-end">
        <PlusCircleIcon className="h-6 w-6 text-primary" />
      </div>
    </div>
  )
}

export default AllergensPage
