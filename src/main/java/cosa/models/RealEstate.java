package cosa.models;

public class RealEstate extends Property
{
    private RealEstateColor color;

    public RealEstateColor getColor()
    {
        return color;
    }


    public void setColor(RealEstateColor color)
    {
        this.color = color;
    }

    @Override
    public String toString()
    {
        return String.format("Name: %s\tPrice: %d\tRent: %d\tOwner: %s\tColor: %s",
                this.getName(), this.getPrice(), this.getRentAmount(), this.getOwner(), color.toString());
    }
}
