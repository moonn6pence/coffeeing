import React from 'react';
import { RadioGroup } from "@headlessui/react";
import classNames from 'classnames';
import { Item } from "../../util/constants"

type TextGridSelectorProps = {
    label: string,
    selectedItem: Item|undefined,
    setSelectedItem: any,
    itemList: Item[];
    containerWidth?: string,
    gridColumnStyle?: string,
};

export const TextGridSelector = ({label, selectedItem, setSelectedItem, itemList, containerWidth="w-96", gridColumnStyle="grid-cols-3"}: TextGridSelectorProps) => {

    return (
        <div className={`flex flex-col gap-1 items-left ${containerWidth}`}>
            <div >
                <label className="w-full text-base text-gray-800 font-medium">{label}</label>
            </div>

            <RadioGroup value={selectedItem} onChange={setSelectedItem}>
            <div className={`grid gap-2 ${gridColumnStyle}`}>
                {itemList.map((item) => (
                <RadioGroup.Option
                    key={item.name}
                    value={item.value}
                    className={
                        ({ active }) =>
                            classNames('cursor-pointer bg-white text-gray-900 shadow-sm',
                            active ? 'ring-2 ring-light-roasting' : '',
                            'group relative flex items-center justify-center rounded-md border py-3 px-4 text-sm font-medium uppercase hover:bg-gray-50 focus:outline-none sm:flex-1 sm:py-6'
                        )
                    }>

                    {({ active, checked }) => (
                    <>
                        <RadioGroup.Label as="span"
                          className={classNames(
                            checked ? 'text-light-roasting font-bold' : '',
                            "z-50"
                          )}
                        >{item.name}</RadioGroup.Label>
                        <span
                            className={
                                classNames(
                                    active ? 'border' : 'border-2',
                                    checked ? 'border-light-roasting bg-light' : 'border-transparent',
                                    'pointer-events-none absolute -inset-px rounded-md'
                                )}
                            aria-hidden="true"/>  
                    </>
                    )}
                </RadioGroup.Option>
                ))}
            </div>
            </RadioGroup>
        </div>
    );
};