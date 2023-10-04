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
    {label:'chocolate', name:'초콜릿'},
    {label:'nutty', name:'견과류'},
    {label:'spicy', name:'매콤함'},
    {label:'floral', name:'꽃향'},
]

import coffeeBean from '../assets/survey/coffee/coffeeBean.png'
import coffeeCapsule from '../assets/survey/coffee/coffeeCapsule.png'

export type CoffeeItem = {
    label:string|number,
		name:string,
		src:string,
		toolTipDesc?:string
}

export const COFFEE_ITEMS = [
	{label:'bean', name:'원두', src:coffeeBean},
	{label:'capsule', name:'캡슐', src:coffeeCapsule}
]

import lightRoast from '../assets/survey/lightRoast.png';
import mediumRoast from '../assets/survey/mediumRoast.png';
import darkRoast from '../assets/survey/darkRoast.png';
import unknownRoast from '../assets/survey/unknownRoast.png';

export const SURVEY_ROAST_ITEMS = [
	{label:0.3, name:'라이트 로스팅',src:lightRoast,toolTipDesc:'강한 신맛이 나고 품종의 특성이 잘 나타납니다.'},
	{label:0.6, name:'미디엄 로스팅',src:mediumRoast,toolTipDesc:'산뜻한 신맛이 나고 품종의 특성이 약하게 나타납니다.'},
	{label:0.9, name:'다크 로스팅',src:darkRoast,toolTipDesc:'신맛이 약해지고 단맛과 쓴맛이 강해집니다.'},
	{label:-1, name:'잘 모르겠어요',src:unknownRoast},
]

import noAcid from '../assets/survey/acidity/noAcid.png';
import lowAcid from '../assets/survey/acidity/lowAcid.png';
import mediumAcid from '../assets/survey/acidity/mediumAcid.png';
import highAcid from '../assets/survey/acidity/highAcid.png';

export const SURVEY_ACID_ITEMS = [
	{label:0.25, name:'없음', src:noAcid},
	{label:0.5, name:'낮음', src:lowAcid},
	{label:0.75, name:'중간', src:mediumAcid},
	{label:1, name:'높음', src:highAcid}
]

import water from '../assets/survey/body/water.png';
import smoothie from '../assets/survey/body/smoothie.png';
import milk from '../assets/survey/body/milk.png';
import unknownBody from '../assets/survey/body/unknownBody.png';

export const SURVEY_BODY_ITEMS = [
	{label:0.3, name:'물 같은 가벼운 느낌',src:water},
	{label:0.6,name:'우유 같은 묵직한 느낌', src:milk},
	{label:0.9,name:'스무디 같은 꾸덕꾸덕한 느낌', src:smoothie},
	{label:-1,name:'잘 모르겠어요', src:unknownBody}
]

import nespresso from '../assets/survey/machine/nespresso-removebg-preview.png'
import nespresso_burtuo from '../assets/survey/machine/nespresso_b-removebg-preview.png'
import dolce from '../assets/survey/machine/dolce-removebg-preview.png'
import illi from '../assets/survey/machine/illi-removebg-preview.png'
import balmuda from '../assets/survey/machine/balmuda.png'

export const SURVEY_MACHINE_ITEMS = [
	{label:1,name:'네스프레소', src:nespresso},
	{label:2,name:'네스프레소 버추오', src:nespresso_burtuo},
	{label:3,name:'돌체구스토', src:dolce},
	{label:4,name:'일리', src:illi},
	{label:5,name:'기타', src:balmuda},
]