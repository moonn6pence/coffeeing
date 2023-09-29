export const API_URL = process.env.REACT_APP_BASE_API_URL;

export type Item = {
    value: number,
    name: string
}

export const GENDER_ITEMS = [
    {
        value:0,
        name: "남성"
    },
    {
        value:1,
        name: "여성"
    },
]

export const AGE_ITEMS = [
    {
        value:0,
        name: "10대"
    },
    {
        value:1,
        name: "20대"
    },
    {
        value:2,
        name: "30대"
    },    {
        value:3,
        name: "40대"
    },    {
        value:4,
        name: "50대"
    },    {
        value:5,
        name: "60대"
    },
];

export type FilterItem  ={
    name:string,
    label:string
}

export const ROAST_ITEMS = [
    {label:'LIGHT', name:'라이트'},
    {label:'MEDIUM_LIGHT', name:'미디엄 라이트'},
    {label:'MEDIUM', name:'미디엄'},
    {label:'MEDIUM_DARK', name:'미디엄 다크'},
    {label:'DARK', name:'다크'},
]

export const ACIDITY_ITEMS = [
    {label:'UNKNOWN', name:'없음'},
    {label:'LOW', name:'낮음'},
    {label:'MEDIUM', name:'중간'},
    {label:'HIGH', name:'높음'},
]

export const BODY_ITEMS = [
    {label:'LIGHT', name:'가벼움'},
    {label:'MEDIUM', name:'중간'},
    {label:'HEAVY', name:'무거움'},
]

export const FLAVOR_NOTE_ITEMS = [
    {label:'fruity', name:'과일'},
    {label:'sweety', name:'달콤'},
    {label:'chocolaty', name:'초콜릿'},
    {label:'nutty', name:'견과류'},
    {label:'spicy', name:'매콤함'},
    {label:'floral', name:'꽃향'},
]

import coffeeBean from '../assets/survey/coffee/coffeeBean.png'
import coffeeCapsule from '../assets/survey/coffee/coffeeCapsule.png'

export type CoffeeItem = {
    label:string,
		name:string,
		src:string,
}

export const COFFEE_ITEMS = [
	{label:'bean', name:'원두', src:coffeeBean},
	{label:'capsule', name:'캡슐', src:coffeeCapsule}
]