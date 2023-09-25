export enum TagType {
    BEAN = "BEAN",
    CAPSULE = "CAPSULE",
}

export interface Tag {
    tagId: number,
    name: string,
    category: TagType,
}