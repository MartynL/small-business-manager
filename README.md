# Small-Business-Manager
A business-generic project that allows small businesses like home bakers or home hairdressers to create price lists and packages. Input orders and track minimum finances. View analytics on their order history so they can make informed decisions / projections based on their selling trends.

# Aims:
Proof of concept: provide a small standalone app that can be installed on a deskptop / laptop to allow individuals - or a few people - to track their small business events. A dashboard on entry will show an overall view of the small business activity. Facilities to manage both orders and items for sale will be provided through a minimal, intuitive interface.

# PoC use cases
  - items for sale, produced by the business:
    - Allows a Business user to enter the items they want to sell and price accordingly.
    - Allow the Business user to create different price lists from the item options they've entered.
    - Allow the Business user to create packages from different items that could be sold as individual units.
    - Allows a Business user control over the management of these items (create, update, delete)
    
  - Price Lists:
    - Can be constructed from items or packages
    ? Can have state enabled / disabled for greater control. If disabled, then orders cannot be created from that price list. Keeps things neater if the business is seasonal and       wants to keep previous price lists but not have them accessible during certain months. Also useful if there are more than one business users.*
    - Allows a Business user control over the management of these items (create, update, delete)
    
  - Packages:
    - Multiple items contained as a single unit with a single price.
    ? Can have state enabled / disabled for greater control. If disabled, then package is removed from price lists and cannot be added to new orders. Keeps things neater if the       business is seasonal and packages are only available for a short time. Also useful if there are more than one business users.*
    - Allows a Business user control over the management of these items (create, update, delete)
    
  - Orders:
    - Allow user to input orders from a particular price list
    - Order state to be tracked:
      -- currently only four stages need to be tracked: Order Created, Order Updated, Order Completed, Order Closed (this could expand out but good enough for PoC)
    - Facility to add discounts to an order, either through direct application by user or by setting up code systems for promotional use.
    
  - Dashboard:
    - provides an overview of**:
      - Orders:
        - visual displays of daily, weekly, monthly, yearly order creation
        - on time orders vs overshot
      - Price lists:
        - List rankings for items (most commonly ordered item)
      - Quick glance at business activity
        - Most recent orders
        - new Price Lists created / disabled / deleted
        
# Technology
Poc will be built in a layered monolithic architecture with Java 11, using spring boot 2.4.0 and Thymeleaf for the view layer. Further development may see this become a Spring Rest api with JS framework (React, Vue, Angular) front-end, but I'm more familiar with Thymeleaf right now and want to get the Poc developed first before I break further into JS frameworks.

# Further Development (dependent on several factors) 
If this takes off and people start using it, I may look to building it out further. Idea's include:
  - Moving from stand alone to web based - cloud backend, client side front-end (moible app front-end)
  - Building out a client facing module where users can sign up and create their own orders.
  - Notifications through the app (may add to PoC) and / or email.
  - Automated messages to the business users when order dates are approaching (may add to PoC)
  ? Order Archived: temporarily archive an order with the understanding that it will be deleted within a certain time limit. Means that if client changes mind, they can             reactivate and the order won't have to be filled out again - can only be reactivated a certain number of times.*
  
    
###### * Haven't decided on whether or not I want to add these features yet.
###### ** I believe this will become more clear through user feedback on what they actually want displayed. Might look at giving the user control over that as well i.e turn on / off      panels and allowing them to choose what's most useful for them to know up front.
